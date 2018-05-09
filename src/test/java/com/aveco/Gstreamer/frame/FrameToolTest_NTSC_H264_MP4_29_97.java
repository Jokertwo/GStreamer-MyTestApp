package com.aveco.Gstreamer.frame;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import com.aveco.Gstreamer.codecs.Codec_NTSC_H264_MP4_29_97;

public class FrameToolTest_NTSC_H264_MP4_29_97 {

    private FrameTool tool;
    
    @Before
    public void setUp() throws Exception {
        tool = new FrameTool();
        tool.setCodec(new Codec_NTSC_H264_MP4_29_97());
    }


    @Test
    public void testGetNumberOfFrame() {       
        assertEquals(0, tool.getNumberOfFrame(0));
        assertEquals(0, tool.getNumberOfFrame(33366665));
        assertEquals(1, tool.getNumberOfFrame(33366666));
        assertEquals(1, tool.getNumberOfFrame(66733332));
        assertEquals(303303, tool.getNumberOfFrame(10120210100001L));
    }

    
    @Test
    public void testGetTimeStampOfFrame() {
        assertEquals(10120210100000L, tool.getTimeStampOfFrame(303303));
        assertEquals(0, tool.getTimeStampOfFrame(0));
    }

}
