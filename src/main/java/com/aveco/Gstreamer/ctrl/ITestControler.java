package com.aveco.Gstreamer.ctrl;

public interface ITestControler {

    void runTests();


    void stopTest();


    void shotDown();


    void step(int count);


    String frameRate();


    String getDuration();


    String getActualFrame();


    String timeForOneFrame();


    String timeCode();


    String actualTimeT();


    String presentationTimeStemp();


    String queryDuration();


    long getVideoEnd();

}
