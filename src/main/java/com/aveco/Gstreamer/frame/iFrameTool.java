package com.aveco.Gstreamer.frame;

import com.aveco.Gstreamer.videoInfo.StepStrategy;
import com.aveco.Gstreamer.videoInfo.VideoType;

public interface iFrameTool {

    boolean addDuration(long duration);


    long getNumberOfFrame(long timeStamp, StepStrategy strategy);


    long getPositionOfFrame(long frame, StepStrategy strategy);
    
    
    void printDur();
    
    
    boolean setDuration(VideoType videoType, StepStrategy stepStrategy);

}