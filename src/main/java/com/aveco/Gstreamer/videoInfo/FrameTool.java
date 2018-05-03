package com.aveco.Gstreamer.videoInfo;

import java.util.LinkedList;
import java.util.List;


public class FrameTool implements IFrameTool {

    private int max = 60;
    private List<Long> frameDuration = new LinkedList<>();


    @Override
    public boolean addDuration(long duration) {
        if (frameDuration.size() < max) {
            frameDuration.add(duration);
            return true;
        }
        return false;
    }


    @Override
    public long getNumberOfFrame(long timeStamp, VideoType videoType) {

        long tempTimeStamp = 0;
        long frame = 0;

        long oldFrame = 0;
        long newFrame = 0;

        for (int i = 0; i < timeStamp / 2; i++) {
            tempTimeStamp = tempTimeStamp + frameDuration.get(i % frameDuration.size());
            newFrame++;
            if (tempTimeStamp >= timeStamp) {
                switch (videoType) {
                    case PAL:
                        return newFrame;
                    case NTSC_DROP:
                        return oldFrame;
                    default:
                        return -1;
                }

            }
            oldFrame = newFrame;
        }
        return frame;
    }


    @Override
    public long getPositionOfFrame(long frame, VideoType videoType) {
        long newPosition = 0;
        if (frame == 0) {
            return newPosition;
        }
        for (int i = 0; i < frame; i++) {
            newPosition = newPosition + frameDuration.get(i % frameDuration.size());
        }
        return newPosition;
    }


    public long getDuration(long queryDuration) {

        long oldDuration = 0;
        long newDuration = 0;

        for (int i = 0; i < queryDuration; i++) {
            oldDuration = newDuration;
            newDuration = newDuration + frameDuration.get(i % frameDuration.size());
            if (newDuration >= queryDuration) {
                break;
            }
        }
        return oldDuration;
    }


    @Override
    public void printDur() {
        for(long item : frameDuration){
            System.out.println(item);
        }
        
    }

}
