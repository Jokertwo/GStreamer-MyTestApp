package com.aveco.Gstreamer.codecs;

import java.util.List;


public interface Codec {

    /**
     * Return list of duration in nanoseconds for each frame
     * 
     * @return
     */
    List<Long> getDurations();


    /**
     * Return sum of duration frame period
     * 
     * @return sum of duration
     */
    long getDurationSum();


    /**
     * Return name of current codec
     * 
     * @return
     */
    String getVideoCodec();


    /**
     * Return format of current codec
     * 
     * @return
     */
    String getFormat();


    /**
     * Return of video container
     * 
     * @return
     */
    String getContainer();


    /**
     * Return frame rate of current codec
     * 
     * @return
     */
    double getFrameRate();


    /**
     * Return true if is it penultimate frame
     * 
     * @return true for penultimate and false for last
     */
    boolean penultimateOrLastFrame();


    long editFrameNumber(long frame);


    long editTimeStamp(long timeStamp);
}
