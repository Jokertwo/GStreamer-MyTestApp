package com.aveco.Gstreamer.videoInfo;

import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.elements.AppSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VideoSinkFrameDuration implements VideoSink {

    private final AppSink videosink;
    private ParseVideo parseVideo;
    private VideoInfo videoInfo;

    private static final Logger logger = LoggerFactory.getLogger(VideoSinkFrameDuration.class);


    /**
     * Creates a new instance of GstVideoComponent
     */
    public VideoSinkFrameDuration(ParseVideo parseVideo, VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
        this.parseVideo = parseVideo;
        this.videosink = new AppSink("GstVideoComponent");
        videosink.set("emit-signals", true);
        videosink.connect(new AppSinkListener());
    }


    @Override
    public Element getElement() {
        return videosink;
    }

    private class AppSinkListener implements AppSink.NEW_SAMPLE {

        @Override
        public FlowReturn newSample(AppSink elem) {
            Sample sample = elem.pullSample();

            videoInfo.setFrameRate(sample.getCaps().getStructure(0).getFraction("framerate").toDouble());
            logger.debug("FrameRate was set (" + videoInfo.getFrameRate() + ")");
            Gst.getExecutor().execute(() -> {
                parseVideo.dispose();
            });

            sample.dispose();
            return FlowReturn.OK;
        }

    }

}
