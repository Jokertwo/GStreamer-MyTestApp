package com.aveco.Gstreamer.frameTool;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import com.aveco.Gstreamer.videoInfo.FrameTool;
import com.aveco.Gstreamer.videoInfo.FrameToolFirst;


public class FrameToolTest {

    private static final long[] NTSC_H_264_A = { 33366666, 33366667, 33366667 };
    private static final long[] NTSC_H_264_B = { 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700,
            33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700,
            33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700,
            33366700, 33366700, 33366701, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700,
            33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700,
            33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700, 33366700,
            33366700, 33366700, 33366701 };
    private static final long[] PAL_H_264 = { 40000000, 40000000, 40000000, 40000000 };
    private FrameTool frame;


    @Before
    public void setUp() {
        frame = new FrameToolFirst();
    }


    @Test
    public void testNTSCGetNumberOfFrameA() {
        prepare(NTSC_H_264_A);
        assertEquals(33, frame.getNumberOfFrame(1101100000L));
        assertEquals(0, frame.getNumberOfFrame(33366665L));
        assertEquals(1, frame.getNumberOfFrame(33366666L));
        assertEquals(1, frame.getNumberOfFrame(33366667L));
    }


    @Test
    public void testNTSCGetPositionOfFrame() {
        prepare(NTSC_H_264_A);
        assertEquals(33366666L, frame.getPositionOfFrame(1L));
        assertEquals(0L, frame.getPositionOfFrame(0L));
        assertEquals(1101100000L, frame.getPositionOfFrame(33L));
    }


    public void testPALGetPositionOfFrame() {
        prepare(PAL_H_264);
        assertEquals(40000000, frame.getPositionOfFrame(1));
    }


    @Test
    public void testPALGetNumberOfFrame() {
        prepare(PAL_H_264);
        assertEquals(1, frame.getNumberOfFrame(40000000L));
        assertEquals(113, frame.getNumberOfFrame(4520000000L));
        assertEquals(616, frame.getNumberOfFrame(24640000001L));
    }


    private void prepare(long[] frameDurations) {
        for (long item : frameDurations) {
            frame.addDuration(item);
        }
    }

}
