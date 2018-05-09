package com.aveco.Gstreamer.frame;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.aveco.Gstreamer.videoInfo.StepStrategy;
import com.aveco.Gstreamer.videoInfo.VideoType;


public class FrameToolImpl implements iFrameTool {

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
    public boolean setDuration(VideoType videoType, StepStrategy stepStrategy) {
        if (stepStrategy == StepStrategy.FROM_0 && videoType == VideoType.NTSC_DROP) {
            frameDuration = new LinkedList<>(Arrays.asList(33366667L, 33366667L, 33366665L));
            return true;
        } else if (stepStrategy == StepStrategy.NON_ZERO && videoType == VideoType.NTSC_DROP) {
            frameDuration = new LinkedList<>(Arrays.asList(33366666L, 33366667L, 33366667L));
            return true;
        } else if (videoType == VideoType.PAL) {
            frameDuration = new LinkedList<>(Arrays.asList(40000000L, 40000000L, 40000000L));
            return true;
        }
        return false;
    }


    @Override
    public long getNumberOfFrame(long timeStamp, StepStrategy strategy) {

        long tempTimeStamp = 0;
        long frame = 0;

        long oldFrame = 0;
        long newFrame = 0;

        for (int i = 0; i < timeStamp / 2; i++) {
            tempTimeStamp = tempTimeStamp + frameDuration.get(i % frameDuration.size());
            newFrame++;
            if (tempTimeStamp >= timeStamp) {
                switch (strategy) {
                    case FROM_0:
                        return newFrame;
                    case NON_ZERO:
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
    public long getPositionOfFrame(long frame, StepStrategy strategy) {
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
        for (long item : frameDuration) {
            System.out.println(item);
        }

    }

}
