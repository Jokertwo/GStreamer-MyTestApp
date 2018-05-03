package com.aveco.Gstreamer.playBin;

import java.net.URI;
import org.freedesktop.gstreamer.Bus.EOS;
import org.freedesktop.gstreamer.Bus.ERROR;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VideoPlayer2 implements IVideoPlayer {

    private static final Logger logger = LoggerFactory.getLogger(VideoPlayer2.class);
    private PlayBin playBin;
    private VideoComponent vComponent;


    public VideoPlayer2() {
        if (Gst.isInitialized()) {
            logger.debug("Create VideoPlayer");
            playBin = new PlayBin("VideoPlayer2");
            vComponent = new VideoComponent();
            addListeners();
            playBin.setVideoSink(vComponent.getElement());
        } else {
            logger.error("GStreamer is not inicialized");
        }
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
                logger.error("GstObject: " + source);
                logger.error("Error code: " + code);
                logger.error("Message: " + message);
            }
        });
        logger.trace("ERROR listener was add to playBin");

    }


    @Override
    public PlayBin getPlayBin() {
        // TODO Auto-generated method stub
        return playBin;
    }


    @Override
    public VideoComponent getVideoCompoment() {
        // TODO Auto-generated method stub
        return vComponent;
    }


    @Override
    public void setUri(URI uri) {
        playBin.stop();
        playBin.setURI(uri);
        playBin.pause();

    }

}
