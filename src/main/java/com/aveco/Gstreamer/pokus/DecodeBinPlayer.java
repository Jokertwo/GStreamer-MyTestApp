package com.aveco.Gstreamer.pokus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.freedesktop.gstreamer.Bin;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Caps;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.Element.PAD_ADDED;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.GhostPad;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.Pipeline;
import org.freedesktop.gstreamer.Structure;
import org.freedesktop.gstreamer.elements.DecodeBin;
import com.aveco.Gstreamer.playBin.VideoComponent;

/**
 * An example of playing audio/video files using a decodebin and manually linking
 * the pads, instead of using a playbin element.
 */
public class DecodeBinPlayer {
    static final String name = "DecodeBinPlayer";
    static JFrame frame;
    static VideoComponent videoComponent;
    static Pipeline pipe;
    
    public static void main(String[] args) {
        // Quartz is abysmally slow at scaling video for some reason, so turn it off.
        System.setProperty("apple.awt.graphics.UseQuartz", "false");
        
        args = Gst.init(name, args);
        if (args.length < 1) {
            System.err.println("Usage: " + name + " <filename>");
            System.exit(1);
        }
        Element src = ElementFactory.make("filesrc", "Input File");
        src.set("location", args[0]);
        
        DecodeBin decodeBin = new DecodeBin("Decode Bin");
        pipe = new Pipeline("main pipeline");
        Element decodeQueue = ElementFactory.make("queue", "Decode Queue");
        pipe.addMany(src, decodeQueue, decodeBin);
        Element.linkMany(src, decodeQueue, decodeBin);

        /* create audio output */
        final Bin audioBin = new Bin("Audio Bin");

        Element conv = ElementFactory.make("audioconvert", "Audio Convert");
        Element resample = ElementFactory.make("audioresample", "Audio Resample");
        Element sink = ElementFactory.make("autoaudiosink", "sink");
        audioBin.addMany(conv, resample, sink);
        Element.linkMany(conv, resample, sink);
        audioBin.addPad(new GhostPad("sink", conv.getStaticPad("sink")));
        pipe.add(audioBin);
        decodeBin.connect(new PAD_ADDED() {
            public void padAdded(Element element, Pad pad) {
                /* only link once */
                if (pad.isLinked()) {
                    return;
                }
                /* check media type */
                Caps caps = pad.getCaps();
                Structure struct = caps.getStructure(0);
                if (struct.getName().startsWith("audio/")) {
                    System.out.println("Linking audio pad: " + struct.getName());
                    pad.link(audioBin.getStaticPad("sink"));
                } else if (struct.getName().startsWith("video/")) {
                    System.out.println("Linking video pad: " + struct.getName());
                    pad.link(videoComponent.getElement().getStaticPad("sink"));
                    
                    // Make the video frame visible
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            frame.setVisible(true);
                        }
                    });
                } else {
                    System.out.println("Unknown pad [" + struct.getName() + "]");
                }
            }
        });
        Bus bus = pipe.getBus();
        
        bus.connect(new Bus.ERROR() {
            public void errorMessage(GstObject source, int code, String message) {
                System.out.println("Error: code=" + code + " message=" + message);
            }
        });
        bus.connect(new Bus.EOS() {

            public void endOfStream(GstObject source) {
                pipe.stop();
                System.exit(0);
            }

        });
        //
        // Do the remainder of the initialization on the Swing EDT
        //
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                frame = new JFrame("DecodeBin Player");

                videoComponent = new VideoComponent();
                videoComponent.setPreferredSize(new Dimension(640, 480));

                frame.add(videoComponent, BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                // Wait until a video pad is linked to make it visible
                frame.setVisible(false);
                pipe.add(videoComponent.getElement());
                pipe.play();
            }
        });
        
    }
}
