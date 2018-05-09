package com.aveco.Gstreamer.codecs;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.videoInfo.VideoInfo;


public class CodecResolver {

    private static final Logger logger = LoggerFactory.getLogger(CodecResolver.class);
    private List<Codec> codecs;


    public CodecResolver() {
        init();
    }


    /**
     * Fill array with all known codecs
     */
    private void init() {
        codecs = new ArrayList<>();
        codecs.add(new Codec_NTSC_H264_MP4_29_97());
        codecs.add(new Codec_Pal_H264_MP4_25());
        codecs.add(new Codec_NTSC_MSVideoV1_AVI_29_97());
        codecs.add(new Codec_NTSC_H264_QuickTime_29_97());
        logger.trace("Inicialize of codecs array was done");
    }


    public Codec resolve(VideoInfo info) {
        double percent = 0;
        Codec codec = null;

        logger.info("Try to resolve codec");
        for (Codec item : codecs) {
            percent = 0;
            if (item.getVideoCodec().equals(info.getVideoCodec())) {
                percent += 33.3;
            }
            if (item.getContainer().equals(info.getContainerFormat())) {
                percent += 33.3;
            }
            if (item.getFrameRate() == info.getFrameRate()) {
                percent += 33.3;
            }
            if (percent > 70) {
                logger.info("Codec was found with with " + percent + "% match");
                codec = item;
                break;
            }
            else{
                logger.warn("Can not find right codec!!!");
            }
        }
        return codec;
    }

}
