package com.aveco.Gstreamer.codecs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Codec_Pal_H264_MP4_25 extends AbstractCodec {

    private long sum;
    private List<Long> durations = new LinkedList<Long>(Arrays.asList(40000000L, 40000000L));

    private String format = "PAL";
    private String codecName = "H.264 (Baseline Profile)";
    private double frameRate = 25.0;
    private String container = "ISO MP4/M4A";


    public Codec_Pal_H264_MP4_25() {
        sum = sumDuration();
    }


    @Override
    public List<Long> getDurations() {
        return durations;
    }


    @Override
    public long getDurationSum() {
        return sum;
    }


    @Override
    public String getVideoCodec() {
        return codecName;
    }


    @Override
    public String getFormat() {
        return format;
    }


    @Override
    public String getContainer() {
        return container;
    }


    @Override
    public double getFrameRate() {
        return frameRate;
    }


    @Override
    public boolean penultimateOrLastFrame() {
        return false;
    }


    @Override
    public long editFrameNumber(long frame) {
        return frame;
    }


    @Override
    public long editTimeStamp(long timeStamp) {
        return timeStamp;
    }

}
