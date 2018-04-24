package com.aveco.Gstreamer.ctrl;

import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Sample;

public interface ITestControler {

    void runTests();


    void stopTest();


    void shotDown();


    void stepForward(int count);
    
    
    void stepBack(int count);
    
    
    void currentPosition();


    String frameRate();


    String getDuration();


    String getActualFrame();


    String timeForOneFrame();


    String timeCode();


    String actualTimeT();


    String presentationTimeStemp();


    String queryDuration();


    long getVideoEnd();
    
    Buffer getBuffer();
    
    Sample getSample();

}
