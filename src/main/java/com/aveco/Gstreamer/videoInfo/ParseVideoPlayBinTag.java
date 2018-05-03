package com.aveco.Gstreamer.videoInfo;

import java.net.URI;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.TagList;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ParseVideoPlayBinTag implements ParseVideo {

    private static final Logger logger = LoggerFactory.getLogger(ParseVideoPlayBinTag.class);
    private URI uri;
    private VideoInfo videoInfo;
    private VideoSink videoSink;
    private Bus.TAG tag;
    private Bus.ASYNC_DONE asyn;
    private PlayBin playBin;


    public ParseVideoPlayBinTag(URI uri, VideoInfo videoInfo) {
        super();
        this.uri = uri;
        this.videoInfo = videoInfo;
        run();
    }



    public synchronized void run() {
        logger.info("Thread for get TAG -> start");
        if (Gst.isInitialized()) {
            playBin = new PlayBin("TagFinder");
            playBin.setURI(uri);

            logger.info("Start find frame duration");
            videoSink = new VideoSinkFrameDuration(this, videoInfo);
            playBin.setVideoSink(videoSink.getElement());

            tag = (GstObject source, TagList tagList) -> videoInfo.parse(tagList);
            asyn = (GstObject source) -> {
                playBin.getBus().disconnect(tag);
                playBin.getBus().disconnect(asyn);
                playBin.setState(State.PLAYING);
            };

            playBin.getBus().connect(tag);
            playBin.getBus().connect(asyn);
            sleep(200);
            playBin.setState(State.PAUSED);
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error("Interupt during wait on result", e);
            }
        } else {
            logger.error("GStreamer is not inicialized");
            
        }
        logger.info("Thread for get TAG -> end");
        
    }


    private void sleep(int count) {
        try {
            Thread.sleep(count);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public synchronized void dispose() {
        notifyAll();
        playBin.setState(State.PAUSED);
        playBin.setState(State.NULL);
        playBin.dispose();
        logger.info("Video proccesing was done");
    }


    @Override
    public PlayBin getPlayBin() {
        return playBin;
    }

}
