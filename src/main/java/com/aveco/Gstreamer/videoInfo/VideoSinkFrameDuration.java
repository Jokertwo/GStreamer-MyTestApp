package com.aveco.Gstreamer.videoInfo;

import org.freedesktop.gstreamer.Buffer;
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

    private long oldValue = 0;
    private boolean OneTime = true;
    private boolean isSetFrameDuration = false;

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
            Buffer buffer = sample.getBuffer();
            long duration = buffer.getPresentationTimestamp().toNanos() - oldValue;
            
            //do this just first time
            if (OneTime) {
                videoInfo.setFrameRate(sample.getCaps().getStructure(0).getFraction("framerate").toDouble());
                logger.debug("FrameRate was set (" + videoInfo.getFrameRate() + ")");
                if (buffer.getPresentationTimestamp().toNanos() != 0) {
                    videoInfo.setStategy(StepStrategy.NON_ZERO);
                } else {
                    videoInfo.setStategy(StepStrategy.FROM_0);
                }
                logger.debug("Step strategy was set (" + videoInfo.getStrategy() + ")");
                isSetFrameDuration = videoInfo.setDuration();
                OneTime = false;
            }
            if (!isSetFrameDuration) {
                if (duration > 0) {
                    
                    if (!videoInfo.addDuration(duration)) {
                        Gst.getExecutor().execute(() -> {
                            parseVideo.dispose();
                        });
                    }
                    oldValue = buffer.getPresentationTimestamp().toNanos();
                    sample.dispose();
                    return FlowReturn.OK;
                }
            } else {
                Gst.getExecutor().execute(() -> {
                    parseVideo.dispose();
                });
            }

            sample.dispose();
            return FlowReturn.OK;
        }

    }

}
