package com.aveco.Gstreamer.videoInfo;

public interface IFrameTool {

    boolean addDuration(long duration);


    long getNumberOfFrame(long timeStamp, StepStrategy strategy);


    long getPositionOfFrame(long frame, StepStrategy strategy);
    
    
    void printDur();
    
    
    boolean setDuration(VideoType videoType, StepStrategy stepStrategy);

}