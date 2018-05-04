package com.aveco.Gstreamer.videoInfo;

import java.net.URI;
import org.freedesktop.gstreamer.Bus;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.GstObject;
import org.freedesktop.gstreamer.SeekFlags;
import org.freedesktop.gstreamer.SeekType;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.Bus.ERROR;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.event.SeekEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ParseVideoPlayBinFindEnd implements ParseVideo {

    private static final Logger logger = LoggerFactory.getLogger(ParseVideoPlayBinFindEnd.class);
    private VideoInfo videoInfo;
    private VideoSink videoSink;
    private PlayBin playBin;
    private URI uri;
    private Bus.ASYNC_DONE asyn;
    private long startTime;


    public ParseVideoPlayBinFindEnd(URI uri, VideoInfo videoInfo) {
        super();
        this.uri = uri;
        this.videoInfo = videoInfo;
        run();
    }


    public synchronized void run() {
        if (Gst.isInitialized()) {
            
            startTime = System.currentTimeMillis();
            logger.info("Start find begin and end of video");
            
            playBin = new PlayBin("VideoEndFinder");
            playBin.setURI(uri);
            videoSink = new VideoSinkFindEnd(videoInfo);
            playBin.setVideoSink(videoSink.getElement());
            
            //connect error listener
            error();
            
            asyn = (GstObject source) -> {
                long queryDuration;
                //wait to duration from GStreamer, wait max 5 sec
                while (true) {
                    queryDuration = playBin.queryDuration().toNanos();
                    if (queryDuration > 0 || System.currentTimeMillis() - startTime > 5000) {
                        break;
                    }
                    sleep(100, logger);
                }
                if (queryDuration > 0) {
                    playBin.getBus().disconnect(asyn);
                    playBin.getBus()
                        .connect((Bus.EOS) (GstObject source2) -> Gst.getExecutor().execute(() -> dispose()));
                    playBin.sendEvent(new SeekEvent(1.0, Format.TIME, SeekFlags.FLUSH, SeekType.SET,
                        playBin.queryDuration().toNanos() - 1000000000, SeekType.NONE, -1));
                    playBin.setState(State.PLAYING);
                } else {
                    playBin.getBus().disconnect(asyn);
                    logger.error("Canot find query duration!!!");
                    dispose();
                }
            };
            playBin.getBus().connect(asyn);
            playBin.pause();
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


    private void error() {
        playBin.getBus().connect(new ERROR() {

            @Override
            public void errorMessage(GstObject source, int code, String message) {
                logger.error("GstObject: " + source);
                logger.error("Error code: " + code);
                logger.error("Message: " + message);
                dispose();
            }
        });
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
