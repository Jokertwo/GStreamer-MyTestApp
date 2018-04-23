package com.aveco.Gstreamer.ctrl;

import java.util.concurrent.TimeUnit;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.ClockTime;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.examples.SimpleVideoComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.gui.IMyGVideoPlayer;


public class VideoPlayerCtrl implements IVideoPlayerCtrl {
    public static final Logger logger = LoggerFactory.getLogger(VideoPlayerCtrl.class);

    private PlayBin pb2;
    private long sec = 1000000000;
    private ITestControler testCtrl;
    private SimpleVideoComponent simpleVC;


    public VideoPlayerCtrl(IMyGVideoPlayer videoPlayer, ITestControler testCtrl) {
        super();
        this.pb2 = videoPlayer.getPlayBin();
        this.testCtrl = testCtrl;
        this.simpleVC = videoPlayer.getSimpleVideoCompoment();
    }


    @Override
    public void play() {
        pb2.play();
        logger.debug("Play video.");
    }


    @Override
    public void pause() {
        pb2.pause();
        logger.debug("Pause video");
    }


    @Override
    public void rewindToStart() {
        pb2.seek(ClockTime.ZERO);
        logger.debug("Video was rewind to begin");
    }


    @Override
    public void rewindToEnd() {
        pb2.seek(testCtrl.getVideoEnd(), TimeUnit.NANOSECONDS);
        logger.debug("Video was rewind to end");
    }


    @Override
    public void rewindOneBack() {
        pb2.seek(pb2.queryPosition(Format.TIME) - sec, TimeUnit.NANOSECONDS);
        logger.debug("Video was rewind one sec back");
    }


    @Override
    public void rewindOneFront() {
        pb2.seek(pb2.queryPosition(Format.TIME) + sec, TimeUnit.NANOSECONDS);
        logger.debug("Video was rewind one sec forward");
    }


    @Override
    public void time() {
        logger.info("Query Position 'buffer' > " + pb2.queryPosition(Format.BUFFERS));
        logger.info("Query Position 'default' > " + pb2.queryPosition(Format.DEFAULT));
        logger.info("Query Position 'percent' > " + pb2.queryPosition(Format.PERCENT));
        logger.info("Query Position 'undefined' > " + pb2.queryPosition(Format.UNDEFINED));
        logger.info("Query Position 'time' > " + pb2.queryPosition(Format.TIME));
    }


    @Override
    public void state() {
        logger.info("State: " + pb2.getState());
    }


    @Override
    public void frameRate() {
        logger.info(testCtrl.frameRate());
    }


    @Override
    public void actualFrame() {
        logger.info(testCtrl.getActualFrame());
    }


    @Override
    public void timeCode() {
        logger.info(testCtrl.timeCode());
    }


    @Override
    public void runTest() {
        testCtrl.runTests();

    }


    @Override
    public void sleep(int value) {
        logger.trace(Thread.currentThread().getName() + "will be slept for " + value + " ms");
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void exit() {
        logger.info("App will be close");
        testCtrl.shotDown();
    }


    @Override
    public void stopTest() {
        testCtrl.stopTest();
    }


    @Override
    public void timeStamp() {
        Sample sample = simpleVC.getAppSink().pullPreroll();
        Buffer buf = sample.getBuffer();
        logger.error("Not inmplemented method!!!");
    }


    @Override
    public void bufferInfo() {
        Sample sample = simpleVC.getAppSink().pullPreroll();
        Buffer buf = sample.getBuffer();
        logger.info("Buffer getDuration: " + buf.getDuration().toNanos());
        logger.info("Buffer getDecodeTimestamp: " + buf.getDecodeTimestamp().toNanos());
        logger.info("Buffer getPresentationTimestamp: " + buf.getPresentationTimestamp().toNanos());
        logger.info("Buffer getOfset: " + buf.getOffset());
        logger.info("Buffer getOfsetEnd: " + buf.getOffsetEnd());

    }


    @Override
    public void stepEvent() {

        testCtrl.step(5);
    }

}
