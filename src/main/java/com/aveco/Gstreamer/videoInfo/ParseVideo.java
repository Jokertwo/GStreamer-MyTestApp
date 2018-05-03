package com.aveco.Gstreamer.videoInfo;

import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;


public interface ParseVideo {

    PlayBin getPlayBin();


    void dispose();


    default void sleep(int count,Logger logger) {
        try {
            Thread.sleep(count);
        } catch (InterruptedException e) {
           logger.error("Interrupt durring sleep!!!" , e);
        }
    }
}
