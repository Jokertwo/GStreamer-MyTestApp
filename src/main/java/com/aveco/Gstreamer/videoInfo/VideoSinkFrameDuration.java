package com.aveco.Gstreamer.videoInfo;

import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.elements.AppSink;


public class VideoSinkFrameDuration implements VideoSink {

    private final AppSink videosink;
    private ParseVideo parseVideo;
    private long oldValue = 0;
    private VideoInfo videoInfo;
    private boolean once = true;


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
            if (duration > 0) {
                videoInfo.setFrameRate(sample.getCaps().getStructure(0).getFraction("framerate").toDouble());
                if (!videoInfo.addDuration(duration)) {
                    if (once) {
                        Gst.getExecutor().execute(() -> {
                            parseVideo.dispose();
                        });
                        once = false;
                    }
                    sample.dispose();
                    return FlowReturn.OK;
                }
            }
            oldValue = buffer.getPresentationTimestamp().toNanos();
            sample.dispose();
            return FlowReturn.OK;
        }

    }

}
