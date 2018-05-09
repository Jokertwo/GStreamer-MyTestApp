package com.aveco.Gstreamer.codecs;

public abstract class AbstractCodec implements Codec {

    
    
    /**
     * Sum of all duration
     * @return
     */
    public long sumDuration() {
        long sum = 0;
        for (long item : getDurations()) {
            sum += item;
        }
        return sum;
    }

}
