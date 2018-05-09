package com.aveco.Gstreamer.videoInfo;

import java.util.concurrent.Callable;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;


public interface ParseVideo extends Callable<VideoInfo>{

    PlayBin getPlayBin();


    void dispose();


    default void sleep(int count,Logger logger) {
        try {
            Thread.sleep(count);
        } catch (InterruptedException e) {
           logger.error("Interrupt durring sleep!!!" , e);
        }
    }
    
    
    default void wait(Logger logger){
        try{
            wait();
        }catch (InterruptedException e) {
            logger.error("Interupt during wait on result", e);
        }
    }
}
