package com.aveco.Gstreamer.gui;

import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import javax.swing.JPanel;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Bus.ASYNC_DONE;
import org.freedesktop.gstreamer.Bus.EOS;
import org.freedesktop.gstreamer.Bus.ERROR;
import org.freedesktop.gstreamer.Bus.TAG;
import org.freedesktop.gstreamer.ElementFactory;
import org.freedesktop.gstreamer.Event;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.Pad;
import org.freedesktop.gstreamer.Pad.EVENT_PROBE;
import org.freedesktop.gstreamer.PadProbeReturn;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.TagList;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.event.TagEvent;
import org.freedesktop.gstreamer.examples.SimpleVideoComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.TagInfo;


@SuppressWarnings("serial")
public class MyGVideoPlayer extends JPanel implements IMyGVideoPlayer {

    private static final Logger logger = LoggerFactory.getLogger(MyGVideoPlayer.class);

    private SimpleVideoComponent vCmp;
    private PlayBin playBin;


    public MyGVideoPlayer(URI uri, URL url) throws MalformedURLException {

        if (Gst.isInitialized()) {

            playBin = new PlayBin("VideoPlayer");
            logger.trace("PlayBin was created");

            vCmp = new SimpleVideoComponent();
            logger.trace("SimpleVideoComponent was created");

            addListeners();
            new Thread(() -> getTag(uri)).start();
//            eventProbe();

            playBin.setVideoSink(vCmp.getElement());
            playBin.setURI(uri);

            setLayout(new BorderLayout());
            add(vCmp, BorderLayout.CENTER);

            playBin.setState(State.PAUSED);

        } else {
            logger.error("GStreamer is not inicialized");
        }
    }


    private void getTag(URI uri) {
        Thread.currentThread().setName("tag-finder");
        PlayBin tagFinder = new PlayBin("TagFinder");
        tagFinder.setVideoSink(ElementFactory.make("fakesink", "videosink"));
        tagFinder.setURI(uri);
        Bus.TAG tag = new TAG() {

            @Override
            public void tagsFound(GstObject source, TagList tagList) {
                TagInfo.getInstance().parse(tagList);
            }
        };
        tagFinder.getBus().connect(tag);
        Bus.ASYNC_DONE asyn = new ASYNC_DONE() {

            @Override
            public void asyncDone(GstObject source) {
                dispose(tagFinder, this, tag);

            }
        };
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tagFinder.getBus().connect(asyn);
        tagFinder.setState(State.PAUSED);

    }


    private void dispose(PlayBin tagFinder, Bus.ASYNC_DONE asyn, Bus.TAG tag) {
        tagFinder.getBus().disconnect(asyn);
        tagFinder.getBus().disconnect(tag);
        tagFinder.setState(State.NULL);
        tagFinder.dispose();
    }


    private void addListeners() {

        playBin.getBus().connect(new EOS() {
            @Override
            public void endOfStream(GstObject source) {
                playBin.pause();
                logger.debug("End of video -> video was paused");
            }
        });
        logger.trace("EOS listener was add to playBin");

        playBin.getBus().connect(new ERROR() {

            @Override
            public void errorMessage(GstObject source, int code, String message) {
                System.out.println("GstObject: " + source);
                System.out.println("Error code: " + code);
                System.out.println("Message: " + message);
            }
        });
        logger.trace("ERROR listener was add to playBin");
    }


    private void eventProbe() {

        Pad videoSinkPad = vCmp.getElement().getStaticPad("sink");
        videoSinkPad.addEventProbe(new EVENT_PROBE() {

            @Override
            public PadProbeReturn eventReceived(Pad pad, Event event) {

                if (event instanceof TagEvent) {
//                    List<String> names = ((TagEvent) event).getTagList().getTagNames();
//
//                    for (String name : names) {
//                        System.out.println(name + " : " + ((TagEvent) event).getTagList().getValue(name, 0));
//
//                    }
                    TagInfo.getInstance().parse(((TagEvent) event).getTagList());
                }
//                else if (event instanceof ReconfigureEvent) {
//                    ReconfigureEvent reg = (ReconfigureEvent) event;
//                    Structure struct = reg.getStructure();
//                    System.out.println("ReconfigureEvent structure: " + struct);
//                }
//                else if (event instanceof LatencyEvent) {
//                    LatencyEvent latency = (LatencyEvent) event;
//                    System.out.println("LatencyEvent :" + latency.getLatency().toNanos());
//                }
//                else if (event instanceof SegmentEvent) {
//                    SegmentEvent segment = (SegmentEvent) event;
//                    System.out.println(segment.getSegment().toString());
//                    
//                }
//                else {
//                    System.out.println(event);
//                }
                return PadProbeReturn.OK;
            }
        });

    }


    @Override
    public PlayBin getPlayBin() {
        return playBin;
    }


    @Override
    public SimpleVideoComponent getSimpleVideoCompoment() {
        return vCmp;
    }


    @Override
    public JPanel getPanel() {
        return this;
    }
}
