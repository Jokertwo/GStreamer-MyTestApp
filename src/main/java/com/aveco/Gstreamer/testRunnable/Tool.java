package com.aveco.Gstreamer.testRunnable;

import org.freedesktop.gstreamer.ClockTime;


public class Tool {

    public static void time() {
        double t = 3599934267600.9342676009342676009;
        long l = 3599934267600L;
        System.out.println(ClockTime.fromNanos(l));
    }
    
    public static void main(String[] args) {
        time();
    }
}
