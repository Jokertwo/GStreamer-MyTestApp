package com.aveco.Gstreamer.ctrl;

import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Sample;
import com.aveco.Gstreamer.videoInfo.VideoInfo;


public interface ITestControler {

    void runTests();


    void stopTest();


    void shotDown();


    void stepForward(int count);


    void stepBack(int count);
    
    
    void setVideoInfo(VideoInfo videoInfo);


    void testAction();


    void playFrameForward(int number);


    void playFrameBack(int number);


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
