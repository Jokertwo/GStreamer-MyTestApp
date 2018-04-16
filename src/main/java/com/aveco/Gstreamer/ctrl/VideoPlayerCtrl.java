package com.aveco.Gstreamer.ctrl;

import java.util.concurrent.TimeUnit;
import org.freedesktop.gstreamer.ClockTime;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.elements.PlayBin;
import com.aveco.Gstreamer.gui.IMyGVideoPlayer;


public class VideoPlayerCtrl implements IVideoPlayerCtrl {

    private PlayBin pb2;
    private long sec = 1000000000;
    ITestControler testCtrl;


    public VideoPlayerCtrl(IMyGVideoPlayer videoPlayer, ITestControler testCtrl) {
        super();
        this.pb2 = videoPlayer.getPlayBin();
        this.testCtrl = testCtrl;
    }


    @Override
    public void play() {
        pb2.play();

    }


    @Override
    public void pause() {
        pb2.pause();
    }


    @Override
    public void rewindToStart() {
        pb2.seek(ClockTime.ZERO);
    }


    @Override
    public void rewindToEnd() {
        pb2.seek(pb2.queryDuration(Format.TIME), TimeUnit.NANOSECONDS);

    }


    @Override
    public void rewindOneBack() {
        pb2.seek(pb2.queryPosition(Format.TIME) - sec, TimeUnit.NANOSECONDS);
    }


    @Override
    public void rewindOneFront() {
        pb2.seek(pb2.queryPosition(Format.TIME) + sec, TimeUnit.NANOSECONDS);

    }


    @Override
    public void time() {
        System.out.println("Query Position 'buffer' > " + pb2.queryPosition(Format.BUFFERS));
        System.out.println("Query Position 'default' > " + pb2.queryPosition(Format.DEFAULT));
        System.out.println("Query Position 'percent' > " + pb2.queryPosition(Format.PERCENT));
        System.out.println("Query Position 'undefined' > " + pb2.queryPosition(Format.UNDEFINED));
        System.out.println("Query Position 'time' > " + pb2.queryPosition(Format.TIME));

    }


    @Override
    public void state() {
        System.out.println(pb2.getState());
    }


    @Override
    public void frameRate() {
        System.out.println(testCtrl.frameRate());
    }


    @Override
    public void actualFrame() {
        System.out.println(testCtrl.getActualFrame());
    }


    @Override
    public void timeCode() {
        System.out.println(testCtrl.timeCode());
    }


    @Override
    public void runTest() {
        testCtrl.runTest();
    }


    @Override
    public void sleep(int value) {
        testCtrl.sleep(value);
    }

}
