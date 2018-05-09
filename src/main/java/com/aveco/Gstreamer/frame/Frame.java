package com.aveco.Gstreamer.frame;

import com.aveco.Gstreamer.codecs.Codec;

public interface Frame {

    /**
     * Set codec which edit behavior of methods
     * 
     * @param codex
     */
    void setCodec(Codec codec);


    /**
     * Return number of frame from begin for current time stamp
     * 
     * @param timeStamp
     *            time from begin in nanoseconds
     * @return return count of frame from begin to current time stamp
     */
    long getNumberOfFrame(long timeStamp);
    
    
    
    
    /**
     * Return time stamp of current frame
     * @param frame
     * @return
     */
    long getTimeStampOfFrame(long frame);

}
