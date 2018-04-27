package com.aveco.Gstreamer.videoInfo;

public interface FrameTool {

    boolean addDuration(long duration);


    long getNumberOfFrame(long timeStamp);


    long getPositionOfFrame(long frame);

}