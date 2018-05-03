package com.aveco.Gstreamer.ctrl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.ClockTime;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.SeekFlags;
import org.freedesktop.gstreamer.SeekType;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.event.SeekEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.playBin.IVideoPlayer;
import com.aveco.Gstreamer.videoInfo.VideoInfo;


public class VideoPlayerCtrlImpl implements VideoPlayerCtrl {
    public static final Logger logger = LoggerFactory.getLogger(VideoPlayerCtrlImpl.class);

    private PlayBin pb2;
    private long sec = 1000000000;
    private ITestControler testCtrl;
    private VideoInfo videoInfo;
    private ExecutorService executor;
    private CommandBuffer buffer;


    public VideoPlayerCtrlImpl(IVideoPlayer videoPlayer,
                               ITestControler testCtrl,
                               VideoInfo videoInfo,
                               ExecutorService executor,
                               CommandBuffer buffer) {
        super();
        this.pb2 = videoPlayer.getPlayBin();
        this.testCtrl = testCtrl;
        this.videoInfo = videoInfo;
        this.executor = executor;
        this.buffer = buffer;
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
        seek(videoInfo.getVideoEnd(videoInfo.getVideoType()));
//        pb2.seek(videoInfo.getVideoEnd(videoInfo.getVideoType()), TimeUnit.NANOSECONDS);
        logger.debug("Video was rewind to end");
    }


    @Override
    public void rewindOneBack(int number) {
        pb2.seek(pb2.queryPosition(Format.TIME) - (sec * number), TimeUnit.NANOSECONDS);
        logger.debug("Video was rewind one sec back");
    }


    @Override
    public void rewindFront(int number) {
        pb2.seek(pb2.queryPosition(Format.TIME) + (sec * number), TimeUnit.NANOSECONDS);
        logger.debug("Video was rewind one sec forward");
    }


    @Override
    public void time() {
        logger.info("Query Position 'buffer' > " + pb2.queryPosition(Format.BUFFERS));
        logger.info("Query Position 'default' > " + pb2.queryPosition(Format.DEFAULT));
        logger.info("Query Position 'percent' > " + pb2.queryPosition(Format.PERCENT));
        logger.info("Query Position 'time' > " + pb2.queryPosition(Format.TIME));
        logger.info("Query Duration 'time' > " + pb2.queryDuration(Format.TIME));
    }


    @Override
    public void state() {
        logger.info("State: " + pb2.getState());
    }


    @Override
    public void frameRate() {
        testCtrl.frameRate();
    }


    @Override
    public void actualFrame() {

        logger.info(videoInfo.getNumberOfFrame(testCtrl.getBuffer().getPresentationTimestamp().toNanos()) + "");
//        testCtrl.getActualFrame();
    }


    @Override
    public void timeCode() {
        testCtrl.timeCode();
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
        shutDownExecutor();
        testCtrl.shotDown();
        pb2.setState(State.NULL);
        pb2.dispose();
        Gst.deinit();
        System.exit(0);
    }


    @Override
    public void stopTest() {
        testCtrl.stopTest();
    }


    @Override
    public void timeStamp() {
        logger.error("Not inmplemented method!!!");
    }


    @Override
    public void bufferInfo() {
        Buffer buf = testCtrl.getBuffer();
        logger.debug("Buffer getDuration: \t" + buf.getDuration().toNanos());
        logger.debug("Buffer getDecodeTimestamp: \t" + buf.getDecodeTimestamp().toNanos());
        logger.debug("Buffer getPresentationTimestamp: \t" + buf.getPresentationTimestamp().toNanos());
        logger.debug("Buffer getOfset: \t" + buf.getOffset());
        logger.debug("Buffer getOfsetEnd: \t" + buf.getOffsetEnd());

    }


    @Override
    public void stepForward(int number) {
        testCtrl.stepForward(number);
    }


    public void stepBack(int number) {
        testCtrl.stepBack(number);
    };


    @Override
    public void TestAction() {
        logger.info(videoInfo.toString());
    }


    @Override
    public void playFrameFront(int number) {
        testCtrl.playFrameForward(number);

    }


    @Override
    public void playFrameBack(int number) {
        testCtrl.playFrameBack(number);
    }


    @Override
    public void seek(long number) {
        logger.info("Seek to '" + number + "'");
        SeekEvent seek = new SeekEvent(1.0, Format.TIME, SeekFlags.FLUSH, SeekType.SET, number, SeekType.NONE, -1);
        if (pb2.sendEvent(seek)) {
            logger.debug("Seek to '" + number + "' was successful");
        } else {
            logger.warn("Seek to '" + number + "' was unsuccessful");
        }
    }


    @Override
    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;

    }


//    @Override
//    public void seek(long number) {
//        logger.info("Seek to '" + number + "'");
//        if (pb2.seek(number, TimeUnit.NANOSECONDS)) {
//            logger.debug("Seek to '" + number + "' was successful");
//        } else {
//            logger.warn("Seek to '" + number + "' was unsuccessful");
//        }
//    }

    private void shutDownExecutor() {
        try {
            logger.info("attempt to shutdown executor");
            executor.shutdown();
        } finally {
            if (!executor.isTerminated()) {
                logger.error("cancel non-finished tasks");
            }
            executor.shutdownNow();
            logger.info("shutdown finished");
        }
    }

}
