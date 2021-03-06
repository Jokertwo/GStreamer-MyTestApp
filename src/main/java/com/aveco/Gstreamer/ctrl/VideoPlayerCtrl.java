package com.aveco.Gstreamer.ctrl;

import com.aveco.Gstreamer.videoInfo.VideoInfo;

public interface VideoPlayerCtrl {

    void actualFrame();


    void bufferInfo();


    void TestAction();


    void exit();


    void frameRate();


    void play();


    void pause();


    void playFrameFront(int number);


    void playFrameBack(int number);


    long getPostion();


    void rewindToStart();


    void rewindToEnd();


    void rewindOneBack(int number);


    void rewindFront(int number);


    void runTest();


    void seek(long number);
    
    
    void setVideoInfo(VideoInfo videoInfo);
    
    
    void setVolume(double volume);


    void sleep(int value);


    void state();


    void stepBack(int number);


    void stepForward(int number);


    void stopTest();


    void time();


    void timeCode();

}
