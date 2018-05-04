package com.aveco.Gstreamer.ctrl;

import com.aveco.Gstreamer.videoInfo.VideoInfo;


public interface ITestControler {

    /**
     * Start tests
     */
    void runTests();


    /**
     * End all tests
     */
    void stopTest();


    /**
     * shutdown executor for test
     */
    void shotDown();


    /**
     * Returns VideoInfo with information about video
     * 
     * @return
     */
    VideoInfo getVideoInfo();


    /**
     * Returns video controller with methods for control video
     * 
     * @return
     */
    VideoPlayerCtrl getVideoPlayerCtrl();


    /**
     * Set videoInfo with information about video
     * 
     * @param videoInfo
     *            VideoInfo
     */
    void setVideoInfo(VideoInfo videoInfo);


    /**
     * Set video controller with methods for control video
     * 
     * @param videoPlayerCtrl
     *            VideoPlayerCtrl
     */
    void setVideoPlayerCtrl(VideoPlayerCtrl videoPlayerCtrl);

}
