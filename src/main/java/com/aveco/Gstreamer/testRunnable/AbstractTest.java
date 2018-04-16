package com.aveco.Gstreamer.testRunnable;

public abstract class AbstractTest implements Runnable{

    
   protected void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
