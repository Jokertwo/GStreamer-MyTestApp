package com.aveco.Gstreamer.videoInfo;

public interface IFrameTool {

    boolean addDuration(long duration);


    long getNumberOfFrame(long timeStamp, VideoType videoType);


    long getPositionOfFrame(long frame, VideoType videoType);
    
    
    void printDur();

}