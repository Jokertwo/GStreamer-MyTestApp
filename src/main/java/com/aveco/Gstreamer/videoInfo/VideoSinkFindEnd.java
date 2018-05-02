package com.aveco.Gstreamer.videoInfo;

import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Element;
import org.freedesktop.gstreamer.FlowReturn;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.elements.AppSink;


public class VideoSinkFindEnd implements VideoSink {

    private AppSink videosink;
    private VideoInfo videoInfo;


    /**
     * Creates a new instance of GstVideoComponent
     */
    public VideoSinkFindEnd(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
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
            videoInfo.setVideoEnd(buffer.getPresentationTimestamp().toNanos());
            return FlowReturn.OK;
        }

    }
}
