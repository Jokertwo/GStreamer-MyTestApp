package com.aveco.Gstreamer.testRunnable;

public abstract class AbstractTest implements Runnable {

    public static final String PATH = "testResolut/";
    private boolean run = true;

    private Object lock = new Object();


    public void stopTest() {
        synchronized (lock) {
            run = false;
        }
    }


    public boolean isRunnig() {
        synchronized (lock) {
            return run;
        }
    }


    protected void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
