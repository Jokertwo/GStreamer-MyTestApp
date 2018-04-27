package com.aveco.Gstreamer.videoInfo;

import java.util.LinkedList;
import java.util.List;


public class FrameToolFirst implements FrameTool {

    private int max = 60;
    private List<Long> frameDuration = new LinkedList<>();


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.FrameTool#addDuration(long)
     */
    @Override
    public boolean addDuration(long duration) {
        if (frameDuration.size() < max) {
            frameDuration.add(duration);
            return true;
        }
        return false;
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.FrameTool#getNumberOfFrame(long)
     */
    @Override
    public long getNumberOfFrame(long timeStamp) {

        long tempTimeStamp = 0;
        long frame = 0;

        long oldFrame = 0;
        long newFrame = 0;

        for (int i = 0; i < timeStamp; i++) {
            tempTimeStamp = tempTimeStamp + frameDuration.get(i % frameDuration.size());
            newFrame++;
            if (tempTimeStamp > timeStamp) {
                return oldFrame;
            } else if (tempTimeStamp == timeStamp) {
                return newFrame;
            }
            oldFrame = newFrame;
        }
        return frame;
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.FrameTool#getPositionOfFrame(long)
     */
    @Override
    public long getPositionOfFrame(long frame) {
        long position = 0;
        if (frame == 0) {
            return position;
        }
        for (int i = 0; i < frame; i++) {
            position = position + frameDuration.get(i % frameDuration.size());
        }
        return position;
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

}
