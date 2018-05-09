package com.aveco.Gstreamer.frame;

import com.aveco.Gstreamer.codecs.Codec;


public class FrameTool implements Frame {

    private Codec codec;


    @Override
    public void setCodec(Codec codec) {
        this.codec = codec;
    }


    @Override
    public long getNumberOfFrame(long timeStamp) {
        long frame = 0;
        if (timeStamp % codec.getDurationSum() == 0) {
            frame = (timeStamp / codec.getDurationSum()) * codec.getDurations().size();
        } else {
            frame = (timeStamp / codec.getDurationSum()) * codec.getDurations().size();
            long rest = timeStamp % codec.getDurationSum();
            long temp = 0;
            int index = 0;
            while (temp <= rest) {
                temp += codec.getDurations().get(index % codec.getDurations().size());
                if (temp <= rest) {
                    index++;
                }
            }
            frame += index;
        }
        return frame;
    }


    @Override
    public long getTimeStampOfFrame(long frame) {
        long timeStamp;
        if (frame == 0) {
            timeStamp = 0;
        } else if (frame % codec.getDurations().size() == 0) {
            timeStamp = (frame / codec.getDurations().size()) * codec.getDurationSum();
        } else {
            timeStamp = 0;
            long rest = frame % codec.getDurations().size();
            for (int i = 0; i < rest; i++) {
                timeStamp += codec.getDurations().get(i % codec.getDurations().size());
            }
            timeStamp += (frame / codec.getDurations().size()) * codec.getDurationSum();
        }
        return timeStamp;
    }

}
