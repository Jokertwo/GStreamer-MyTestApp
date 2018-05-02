package com.aveco.Gstreamer.videoInfo;

import java.util.List;
import org.freedesktop.gstreamer.TagList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VideoInfoImpl implements VideoInfo {

    public static final Logger logger = LoggerFactory.getLogger(VideoInfoImpl.class);

    private String audioCodec;
    private String datetime;
    private String containerFormat;
    private String videoCodec;
    private String languageCode;
    private String bitRate;
    private double frameRate = 0;
    private long videoEndPAL;
    private long videoEndNTSC;
    private VideoType videoType;

    private static final String aCodec = "audio-codec";
    private static final String dTime = "datetime";
    private static final String cFormat = "container-format";
    private static final String vCodec = "video-codec";
    private static final String lCode = "language-code";
    private static final String bRate = "bitrate";

    private IFrameTool frameTool;


    public VideoInfoImpl() {
        this.frameTool = new FrameTool();
    }


    @Override
    public boolean addDuration(long duration) {
        return frameTool.addDuration(duration);
    }


    @Override
    public long getNumberOfFrame(long timeStamp) {
        return frameTool.getNumberOfFrame(timeStamp, videoType);
    }


    @Override
    public long getPositionOfFrame(long frame) {
        return frameTool.getPositionOfFrame(frame, videoType);
    }


    @Override
    public String getBitRate() {
        return bitRate;
    }


    private void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }


    @Override
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


    @Override
    public String getVideoCodec() {
        return videoCodec;
    }


    private void setVideoCodec(String videoCodec) {
        if (this.videoCodec == null) {
            this.videoCodec = videoCodec;
        }
    }


    @Override
    public String getAudioCodec() {
        return audioCodec;
    }


    private void setAudioCodec(String audioCodec) {
        if (this.audioCodec == null) {
            this.audioCodec = audioCodec;
        }
    }


    @Override
    public String getDatetime() {
        return datetime;
    }


    private void setDatetime(String datetime) {
        if (this.datetime == null) {
            this.datetime = datetime;
        }
    }


    @Override
    public String getContainerFormat() {
        return containerFormat;
    }


    private void setContainerFormat(String containerFormat) {
        if (this.containerFormat == null) {
            this.containerFormat = containerFormat;
        }
    }


    @Override
    public String getLanguageCode() {
        return languageCode;
    }


    private void setLanguageCode(String languageCode) {
        if (this.languageCode == null) {
            this.languageCode = languageCode;
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(aCodec + ":\t" + getAudioCodec() + "\n");
        sb.append(vCodec + ":\t" + getVideoCodec() + "\n");
        sb.append(dTime + ":\t" + getDatetime() + "\n");
        sb.append(cFormat + ":\t" + getContainerFormat() + "\n");
        sb.append(lCode + ":\t" + getLanguageCode() + "\n");
        sb.append(bRate + ":\t" + getBitRate() + "\n");
        sb.append("frame rate" + ":\t" + frameRate + "\n");
        sb.append("VideoType" + ":\t" + videoType);
        frameTool.printDur();
        return sb.toString();
    }


    @Override
    public void setFrameRate(double frameRate) {
        // set just fisrt time
        if (videoType == null && frameRate > 0) {
            int trunc = (int) frameRate;
            if (trunc == 25) {
                videoType = VideoType.PAL;
            } else if (trunc == 29) {
                videoType = VideoType.NTSC;
            } else {
                logger.error("Unknown format: " + frameRate);
            }
            this.frameRate = frameRate;
        }
    }


    @Override
    public VideoType getVideoType() {
        return videoType;
    }


    @Override
    public double getFrameRate() {
        return frameRate;
    }


    @Override
    public void setVideoEnd(long videoEnd) {
        videoEndNTSC = videoEndPAL;
        videoEndPAL = videoEnd;
    }


    @Override
    public long getVideoEnd(VideoType videoType) {
        if (videoType == VideoType.PAL) {
            return videoEndPAL;
        }
        return videoEndNTSC;

    }
}
