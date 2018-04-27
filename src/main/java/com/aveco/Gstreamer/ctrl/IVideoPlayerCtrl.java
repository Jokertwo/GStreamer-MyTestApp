package com.aveco.Gstreamer.ctrl;

public interface IVideoPlayerCtrl {

    void actualFrame();


    void bufferInfo();


    void TestAction();


    void exit();


    void frameRate();


    void play();


    void pause();


    void playFrameFront(int number);


    void playFrameBack(int number);


    void rewindToStart();


    void rewindToEnd();


    void rewindOneBack(int number);


    void rewindFront(int number);


    void runTest();


    void seek(long number);


    void sleep(int value);


    void state();


    void stepBack(int number);


    void stepForward(int number);


    void stopTest();


    void time();


    void timeCode();


    void timeStamp();

}
