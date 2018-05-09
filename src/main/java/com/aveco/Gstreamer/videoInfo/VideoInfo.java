package com.aveco.Gstreamer.videoInfo;

import java.util.List;
import org.freedesktop.gstreamer.TagList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.codecs.Codec;
import com.aveco.Gstreamer.codecs.CodecResolver;
import com.aveco.Gstreamer.frame.Frame;
import com.aveco.Gstreamer.frame.FrameTool;


public class VideoInfo {

    public static final Logger logger = LoggerFactory.getLogger(VideoInfo.class);

    private String audioCodec;
    private String datetime;
    private String containerFormat;
    private String videoCodec;
    private String languageCode;
    private String bitRate;
    private double frameRate = 0;
    private Codec codec;

    private long oldEnd = 0;
    private long newEnd = 0;

    private Frame frametool;

    private static final String aCodec = "audio-codec";
    private static final String dTime = "datetime";
    private static final String cFormat = "container-format";
    private static final String vCodec = "video-codec";
    private static final String lCode = "language-code";
    private static final String bRate = "bitrate";


    public VideoInfo() {
        frametool = new FrameTool();
    }


    public void parse(TagList tagList) {
        List<String> names = tagList.getTagNames();

        for (String name : names) {

            switch (name) {
                case aCodec:
                    setAudioCodec(tagList.getValue(name, 0).toString());
                    break;
                case vCodec:
                    setVideoCodec(tagList.getValue(name, 0).toString());
                    break;
                case dTime:
                    setDatetime(tagList.getValue(name, 0).toString());
                    break;
                case cFormat:
                    setContainerFormat(tagList.getValue(name, 0).toString());
                    break;
                case lCode:
                    setLanguageCode(tagList.getValue(name, 0).toString());
                    break;
                case bRate:
                    setBitRate(tagList.getValue(name, 0).toString());
                    break;
            }
        }
    }


    public void setVideoEnd(long timeStamp) {
        oldEnd = newEnd;
        newEnd = timeStamp;
    }


    public boolean resolveCodec() {
        CodecResolver codecResolver = new CodecResolver();
        codec = codecResolver.resolve(this);
        if (codec == null) {
            return false;
        }
        frametool.setCodec(codec);
        return true;
    }


    public void setFrameRate(double framerate) {
        if (this.frameRate == 0) {
            this.frameRate = framerate;
        }
    }


    public double getFrameRate() {
        return frameRate;
    }


    public String getVideoCodec() {
        return videoCodec;
    }


    private void setVideoCodec(String videoCodec) {
        if (this.videoCodec == null) {
            this.videoCodec = videoCodec;
        }
    }


    public String getAudioCodec() {
        return audioCodec;
    }


    private void setAudioCodec(String newAudioCodec) {
        if (audioCodec == null) {
            audioCodec = newAudioCodec;
        }
    }


    public String getDatetime() {
        return datetime;
    }


    private void setDatetime(String datetime) {
        if (this.datetime == null) {
            this.datetime = datetime;
        }
    }


    public String getContainerFormat() {
        return containerFormat;
    }


    private void setContainerFormat(String containerFormat) {
        if (this.containerFormat == null) {
            this.containerFormat = containerFormat;
        }
    }


    public String getLanguageCode() {
        return languageCode;
    }


    private void setLanguageCode(String languageCode) {
        if (this.languageCode == null) {
            this.languageCode = languageCode;
        }
    }


    public String getBitRate() {
        return bitRate;
    }


    private void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }


    public long getVideoEnd() {
        if (codec.penultimateOrLastFrame()) {
            return codec.editTimeStamp(oldEnd);
        }
        return codec.editTimeStamp(newEnd);
    }


    public long getNumberOfFrame(long timeStamp) {
        return codec.editFrameNumber(frametool.getNumberOfFrame(timeStamp));
    }


    public long getTimeStampOfFrame(long frame) {
        return codec.editTimeStamp(frametool.getTimeStampOfFrame(frame));
    }


    public void print() {
        logger.info(aCodec + " : \t" + audioCodec);
        logger.info(dTime + " : \t" + datetime);
        logger.info(cFormat + " : \t" + containerFormat);
        logger.info(vCodec + " : \t" + videoCodec);
        logger.info(lCode + " : \t" + languageCode);
        logger.info(bRate + " : \t" + bitRate);
        logger.info("Frame rate : \t" + frameRate);
    }

}