package com.aveco.Gstreamer.ctrl;

public interface IVideoPlayerCtrl {

    void actualFrame();


    void bufferInfo();


    void currentPosition();


    void exit();


    void frameRate();


    void play();


    void pause();


    void rewindToStart();


    void rewindToEnd();


    void rewindOneBack();


    void rewindOneFront();


    void runTest();


    void sleep(int value);


    void state();


    void stepBack();


    void stepForward();


    void stopTest();


    void time();


    void timeCode();


    void timeStamp();

}
