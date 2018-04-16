package com.aveco.Gstreamer.ctrl;

public interface ITestControler {

    void runTest();


    void shotDown();


    String frameRate();


    String getDuration();


    String getActualFrame();


    String timeForOneFrame();


    String timeCode();


    String actualTimeT();


    String actualTimeP();

}
