package com.aveco.Gstreamer.videoInfo;

import org.freedesktop.gstreamer.TagList;


public interface VideoInfo {

    public abstract boolean addDuration(long duration);


    public abstract long getNumberOfFrame(long timeStamp);


    public abstract long getPositionOfFrame(long frame);


    public abstract String getBitRate();


    public abstract void parse(TagList tagList);


    public abstract String getVideoCodec();


    public abstract String getAudioCodec();


    public abstract String getDatetime();


    public abstract String getContainerFormat();


    public abstract String getLanguageCode();

}