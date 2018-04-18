package com.aveco.Gstreamer.ctrl;

import org.freedesktop.gstreamer.examples.SimpleVideoComponent;

public interface IVideoPlayerCtrl {

    
    void play();
    
    void pause();
    
    void rewindToStart();
    
    void rewindToEnd();
    
    void rewindOneBack();
    
    void rewindOneFront();
    
    void time();
    
    void timeCode();
    
    void state();
            
    void actualFrame();
    
    void frameRate();
    
    void runTest();
    
    void sleep(int value);
    
    void exit();
    
    void stopTest();
    
    SimpleVideoComponent getSimpleVideoComponent();
    
}
