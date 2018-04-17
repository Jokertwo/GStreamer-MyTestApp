package com.aveco.Gstreamer.ctrl;

public interface ITestControler {

    void runTests();

    void stopTest();

    void shotDown();


    String frameRate();


    String getDuration();


    String getActualFrame();


    String timeForOneFrame();


    String timeCode();


    String actualTimeT();


    String actualTimeP();
    
    String queryDuration();

}
