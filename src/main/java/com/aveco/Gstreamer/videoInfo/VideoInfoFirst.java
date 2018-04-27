package com.aveco.Gstreamer.videoInfo;

import java.util.List;
import org.freedesktop.gstreamer.TagList;


public class VideoInfoFirst implements VideoInfo {

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

    private FrameTool frameTool;


    public VideoInfoFirst() {
        this.frameTool = new FrameToolFirst();
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.VideoInfo#addDuration(long)
     */
    @Override
    public boolean addDuration(long duration) {
        return frameTool.addDuration(duration);
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.VideoInfo#getNumberOfFrame(long)
     */
    @Override
    public long getNumberOfFrame(long timeStamp) {
        return frameTool.getNumberOfFrame(timeStamp);
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.VideoInfo#getPositionOfFrame(long)
     */
    @Override
    public long getPositionOfFrame(long frame) {
        return frameTool.getPositionOfFrame(frame);
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.VideoInfo#getBitRate()
     */
    @Override
    public String getBitRate() {
        return bitRate;
    }


    private void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.VideoInfo#parse(org.freedesktop.gstreamer.TagList)
     */
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


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.VideoInfo#getVideoCodec()
     */
    @Override
    public String getVideoCodec() {
        return videoCodec;
    }


    private void setVideoCodec(String videoCodec) {
        if (this.videoCodec == null) {
            this.videoCodec = videoCodec;
        }
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.VideoInfo#getAudioCodec()
     */
    @Override
    public String getAudioCodec() {
        return audioCodec;
    }


    private void setAudioCodec(String audioCodec) {
        if (this.audioCodec == null) {
            this.audioCodec = audioCodec;
        }
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.VideoInfo#getDatetime()
     */
    @Override
    public String getDatetime() {
        return datetime;
    }


    private void setDatetime(String datetime) {
        if (this.datetime == null) {
            this.datetime = datetime;
        }
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.VideoInfo#getContainerFormat()
     */
    @Override
    public String getContainerFormat() {
        return containerFormat;
    }


    private void setContainerFormat(String containerFormat) {
        if (this.containerFormat == null) {
            this.containerFormat = containerFormat;
        }
    }


    /* (non-Javadoc)
     * @see com.aveco.Gstreamer.videoInfo.VideoInfo#getLanguageCode()
     */
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
        sb.append(aCodec + " : \t" + getAudioCodec() + "\n");
        sb.append(vCodec + " : \t" + getVideoCodec() + "\n");
        sb.append(dTime + " : \t" + getDatetime() + "\n");
        sb.append(cFormat + " : \t" + getContainerFormat() + "\n");
        sb.append(lCode + " : \t" + getLanguageCode() + "\n");
        sb.append(bRate + " : \t" + getBitRate());
        return sb.toString();
    }
}
