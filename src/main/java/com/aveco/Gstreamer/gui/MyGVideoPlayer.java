package com.aveco.Gstreamer.gui;

import java.awt.BorderLayout;
import java.net.URI;
import java.util.List;
import javax.swing.JPanel;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Bus.EOS;
import org.freedesktop.gstreamer.Bus.ERROR;
import org.freedesktop.gstreamer.Bus.STATE_CHANGED;
import org.freedesktop.gstreamer.Bus.TAG;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.TagList;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.examples.SimpleVideoComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("serial")
public class MyGVideoPlayer extends JPanel implements IMyGVideoPlayer {

    private static final Logger logger = LoggerFactory.getLogger(MyGVideoPlayer.class);

    private SimpleVideoComponent vCmp;
    private PlayBin playBin;

    private Bus.TAG tag;


    public MyGVideoPlayer(URI uri) {

        if (Gst.isInitialized()) {

            playBin = new PlayBin("VideoPlayer");
            logger.trace("PlayBin was created");

            vCmp = new SimpleVideoComponent();
            logger.trace("SimpleVideoComponent was created");

            addListeners();

            playBin.setVideoSink(vCmp.getElement());
            playBin.setURI(uri);

            setLayout(new BorderLayout());
            add(vCmp, BorderLayout.CENTER);

            playBin.setState(State.PAUSED);

        } else {
            logger.error("GStreamer is not inicialized");
        }
    }


    private void addListeners() {
        tag = new TAG() {

            @Override
            public void tagsFound(GstObject source, TagList tagList) {
                List<String> names = tagList.getTagNames();

                for (String name : names) {
                    System.out.println(name + " : " + tagList.getValue(name, 0));

                }

            }

        };
        playBin.getBus().connect(tag);

        playBin.getBus().connect(new STATE_CHANGED() {

            @Override
            public void stateChanged(GstObject source, State old, State current, State pending) {
                System.out.println(old + "\t" + current + "\t" + pending);

            }
        });

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
