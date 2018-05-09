package com.aveco.Gstreamer.codecs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Codec_NTSC_MSVideoV1_AVI_29_97 extends AbstractCodec {

    private long sum;
    private List<Long> durations = new LinkedList<Long>(Arrays.asList(33366668L, 33366667L, 33366665L));

    private String format = "NTSC";
    private String codecName = "MS video v1";
    private double frameRate = 29.97002997002997;
    private String container = "AVI";


    public Codec_NTSC_MSVideoV1_AVI_29_97() {
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

        return timeStamp +1;
    }

}
