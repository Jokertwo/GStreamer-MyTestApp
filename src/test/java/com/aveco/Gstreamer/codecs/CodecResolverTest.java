package com.aveco.Gstreamer.codecs;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class CodecResolverTest {

    private CodecResolver resolver;


    @Before
    public void setUp() throws Exception {
        resolver = new CodecResolver();
    }


    @Test
    public void testResolveCodec_NTSC_H264_MP4_29_97() {
        assertEquals(new Codec_NTSC_H264_MP4_29_97(), resolver);
    }

}
