package com.aveco.Gstreamer.ctrl;

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
     * Returns video controller with methods for control video
     * 
     * @return
     */
    VideoPlayerCtrl getVideoPlayerCtrl();


    /**
     * Set video controller with methods for control video
     * 
     * @param videoPlayerCtrl
     *            VideoPlayerCtrl
     */
    void setVideoPlayerCtrl(VideoPlayerCtrl videoPlayerCtrl);

}
