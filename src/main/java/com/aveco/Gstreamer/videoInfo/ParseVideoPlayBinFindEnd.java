package com.aveco.Gstreamer.videoInfo;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ParseVideoPlayBinFindEnd implements ParseVideo {

    private static final Logger logger = LoggerFactory.getLogger(ParseVideoPlayBinFindEnd.class);
    private VideoInfo videoInfo;
    private VideoSink videoSink;
    private PlayBin playBin;
    private URI uri;
    private Bus.ASYNC_DONE asyn;


    public ParseVideoPlayBinFindEnd(URI uri, VideoInfo videoInfo) {
        super();
        this.uri = uri;
        this.videoInfo = videoInfo;
        run();
    }


    public synchronized void run() {
        if (Gst.isInitialized()) {
            logger.info("Start find begin and end of video");
            playBin = new PlayBin("VideoEndFinder");
            playBin.setURI(uri);
            videoSink = new VideoSinkFindEnd(videoInfo);
            playBin.setVideoSink(videoSink.getElement());

            playBin.getBus().connect((Bus.EOS) (GstObject source) -> Gst.getExecutor().execute(() -> dispose()));
            asyn = (GstObject source) -> {
                playBin.getBus().disconnect(asyn);
                playBin.seek(playBin.queryDuration().toNanos() - 1000000000, TimeUnit.NANOSECONDS);
                playBin.setState(State.PLAYING);
            };
            playBin.getBus().connect(asyn);
            playBin.setState(State.PAUSED);
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error("Interupt during wait on result", e);
            }
            logger.info("Find begin and end of video was done");
        } else {
            logger.error("GStreamer is not inicialized.");
        }

    }


    @Override
    public synchronized void dispose() {
        notifyAll();
        playBin.unlink(videoSink.getElement());
        playBin.setState(State.PAUSED);
        playBin.setState(State.NULL);
        playBin.dispose();
    }


    @Override
    public PlayBin getPlayBin() {
        return playBin;
    }

}
