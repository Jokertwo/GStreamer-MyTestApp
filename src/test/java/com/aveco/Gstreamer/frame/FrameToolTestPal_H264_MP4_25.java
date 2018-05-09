package com.aveco.Gstreamer.frame;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import com.aveco.Gstreamer.codecs.Codec_Pal_H264_MP4_25;

public class FrameToolTestPal_H264_MP4_25 {

    
    private FrameTool tool;
    
    @Before
    public void setUp() throws Exception {
        tool = new FrameTool();
    }


    @Test
    public void testGetNumberOfFrame() {
        tool.setCodec(new Codec_Pal_H264_MP4_25());
        assertEquals(0, tool.getNumberOfFrame(0));
        assertEquals(0, tool.getNumberOfFrame(39999999));
        assertEquals(1, tool.getNumberOfFrame(40000001));
        assertEquals(2, tool.getNumberOfFrame(80000000));
        assertEquals(2, tool.getNumberOfFrame(80000001));
        assertEquals(3, tool.getNumberOfFrame(120000001));
        assertEquals(101101, tool.getNumberOfFrame(4044040000000L));
        
    }

 
    @Test
    public void testGetTimeStampOfFrame() {
        tool.setCodec(new Codec_Pal_H264_MP4_25());
        assertEquals(0,tool.getTimeStampOfFrame(0));
        assertEquals(40000000, tool.getTimeStampOfFrame(1));
        assertEquals(120000000, tool.getTimeStampOfFrame(3));
    }

}
