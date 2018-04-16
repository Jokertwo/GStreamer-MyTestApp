package com.aveco.Gstreamer.ctrl;

public interface ITestControler {

    
    void runTest();
    
    String frameRate();
    
    String duration();
    
    int getActualFrame();
    
    double timeForOneFrame();
    
    double getFrameRate();
    
    void sleep(long time);
    
    String timeCode();
    
}
