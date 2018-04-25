package com.aveco.Gstreamer;

import java.util.List;
import org.freedesktop.gstreamer.TagList;


public class TagInfo {

    private static TagInfo info;

    private String audioCodec;
    private String datetime;
    private String containerFormat;
    private String videoCodec;
    private String languageCode;
    private String bitRate;

    private static final String aCodec = "audio-codec";
    private static final String dTime = "datetime";
    private static final String cFormat = "container-format";
    private static final String vCodec = "video-codec";
    private static final String lCode = "language-code";
    private static final String bRate = "bitrate";


    private TagInfo() {
    }


    public String getBitRate() {
        return bitRate;
    }
    


    private synchronized void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }
    


    public static TagInfo getInstance() {
        if (info == null) {
            synchronized (TagInfo.class) {
                if (info == null) {
                    info = new TagInfo();
                }
            }
        }
        return info;
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


    public String getVideoCodec() {
        return videoCodec;
    }


    private synchronized void setVideoCodec(String videoCodec) {
        if (this.videoCodec == null) {
            this.videoCodec = videoCodec;
        }
    }


    public String getAudioCodec() {
        return audioCodec;
    }


    private synchronized void setAudioCodec(String audioCodec) {
        if (this.audioCodec == null) {
            this.audioCodec = audioCodec;
        }
    }


    public String getDatetime() {
        return datetime;
    }


    private synchronized void setDatetime(String datetime) {
        if (this.datetime == null) {
            this.datetime = datetime;
        }
    }


    public String getContainerFormat() {
        return containerFormat;
    }


    private synchronized void setContainerFormat(String containerFormat) {
        if (this.containerFormat == null) {
            this.containerFormat = containerFormat;
        }
    }


    public String getLanguageCode() {
        return languageCode;
    }


    private synchronized void setLanguageCode(String languageCode) {
        if (this.languageCode == null) {
            this.languageCode = languageCode;
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(aCodec + " : \t" + getAudioCodec() + "\n");
        sb.append(vCodec + " : \t" + getVideoCodec() + "\n");
        sb.append(dTime + " : \t" + getDatetime() + "\n");
        sb.append(cFormat + " : \t" + getContainerFormat() + "\n");
        sb.append(lCode + " : \t" + getLanguageCode() + "\n");
        sb.append(bRate + " : \t" + getBitRate());
        return sb.toString();
    }
}
