package com.aveco.Gstreamer.videoInfo;

import org.freedesktop.gstreamer.TagList;


public interface VideoInfo {

    boolean addDuration(long duration);


    long getNumberOfFrame(long timeStamp);


    long getPositionOfFrame(long frame);


    String getBitRate();


    void parse(TagList tagList);


    void setFrameRate(double frameRate);


    void setVideoEnd(long videoEnd);


    long getVideoEnd(VideoType videoType);


    VideoType getVideoType();


    double getFrameRate();


    String getVideoCodec();


    String getAudioCodec();


    String getDatetime();


    String getContainerFormat();


    String getLanguageCode();

}