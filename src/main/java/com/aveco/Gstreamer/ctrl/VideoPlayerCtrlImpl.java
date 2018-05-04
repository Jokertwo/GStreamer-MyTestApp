package com.aveco.Gstreamer.ctrl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.ClockTime;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.SeekFlags;
import org.freedesktop.gstreamer.SeekType;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.event.SeekEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.gui.GUI;
import com.aveco.Gstreamer.playBin.IVideoPlayer;
import com.aveco.Gstreamer.playBin.VideoComponent;
import com.aveco.Gstreamer.videoInfo.VideoInfo;


public class VideoPlayerCtrlImpl implements VideoPlayerCtrl {
    public static final Logger logger = LoggerFactory.getLogger(VideoPlayerCtrlImpl.class);

    private PlayBin playBin;
    private long sec = 1000000000;
    private ITestControler testCtrl;
    private VideoInfo videoInfo;
    private ExecutorService executor;
    private VideoComponent videoComponent;


    public VideoPlayerCtrlImpl(IVideoPlayer videoPlayer,
                               ITestControler testCtrl,
                               VideoInfo videoInfo,
                               ExecutorService executor) {
        super();
        this.playBin = videoPlayer.getPlayBin();
        this.videoComponent = videoPlayer.getVideoCompoment();
        this.testCtrl = testCtrl;
        this.videoInfo = videoInfo;
        this.executor = executor;
    }


    @Override
    public void play() {
        playBin.play();
        logger.debug("Play video.");
    }


    @Override
    public void pause() {
        playBin.pause();
        logger.debug("Pause video");
    }


    @Override
    public void rewindToStart() {
        playBin.seek(ClockTime.ZERO);
        logger.debug("Video was rewind to begin");
    }


    @Override
    public void rewindToEnd() {
        seek(videoInfo.getVideoEnd());
//        pb2.seek(videoInfo.getVideoEnd(videoInfo.getVideoType()), TimeUnit.NANOSECONDS);
        logger.debug("Video was rewind to end");
    }


    @Override
    public void rewindOneBack(int number) {
        playBin.seek(playBin.queryPosition(Format.TIME) - (sec * number), TimeUnit.NANOSECONDS);
        logger.debug("Video was rewind one sec back");
    }


    @Override
    public void rewindFront(int number) {
        playBin.seek(playBin.queryPosition(Format.TIME) + (sec * number), TimeUnit.NANOSECONDS);
        logger.debug("Video was rewind one sec forward");
    }


    @Override
    public void time() {
        logger.info("Query Position 'buffer' > " + playBin.queryPosition(Format.BUFFERS));
        logger.info("Query Position 'default' > " + playBin.queryPosition(Format.DEFAULT));
        logger.info("Query Position 'percent' > " + playBin.queryPosition(Format.PERCENT));
        logger.info("Query Position 'time' > " + playBin.queryPosition(Format.TIME));
        logger.info("Query Duration 'time' > " + playBin.queryDuration(Format.TIME));
    }


    @Override
    public void state() {
        logger.info("State: " + playBin.getState());
    }


    @Override
    public void frameRate() {
        videoInfo.getFrameRate();
    }


    @Override
    public void actualFrame() {
        logger.info(videoInfo.getNumberOfFrame(getBuffer().getPresentationTimestamp().toNanos()) + "");
    }


    @Override
    public void timeCode() {
        logger.warn("Unimplemted methods!!!");
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
        GUI.timer.stop();
        shutDownExecutor();
        testCtrl.shotDown();
        playBin.setState(State.NULL);
        playBin.dispose();
        Gst.deinit();
        System.exit(0);
    }


    @Override
    public void stopTest() {
        testCtrl.stopTest();
    }


    @Override
    public void bufferInfo() {
        Buffer buf = getBuffer();
        logger.debug("Buffer getDuration: \t" + buf.getDuration().toNanos());
        logger.debug("Buffer getDecodeTimestamp: \t" + buf.getDecodeTimestamp().toNanos());
        logger.debug("Buffer getPresentationTimestamp: \t" + buf.getPresentationTimestamp().toNanos());
        logger.debug("Buffer getOfset: \t" + buf.getOffset());
        logger.debug("Buffer getOfsetEnd: \t" + buf.getOffsetEnd());

    }


    @Override
    public void stepForward(int count) {
        logger.debug("Step " + count + " frame/s forward");

        long positon = getBuffer().getPresentationTimestamp().toNanos();
        long frame = videoInfo.getNumberOfFrame(positon);
        long newPosition = videoInfo.getPositionOfFrame(frame + count);

        logger.trace("New position: " + newPosition);
        SeekEvent step = new SeekEvent(1.0, Format.TIME, SeekFlags.FLUSH, SeekType.SET, newPosition, SeekType.NONE,
            -1);

        if (!playBin.sendEvent(step)) {
            stopTest();
            logger.error("Error during step forward");
        } else {
            logger.trace("Step forward was succesfull");
        }
    }


    public void stepBack(int count) {
        logger.debug("Step " + count + " frame/s back");

        long positon = getBuffer().getPresentationTimestamp().toNanos();
        long frame = videoInfo.getNumberOfFrame(positon);
        long newPosition = videoInfo.getPositionOfFrame(frame - count > 0 ? frame - count : 0);

        logger.trace("New position: " + newPosition);
        SeekEvent step = new SeekEvent(1.0, Format.TIME, SeekFlags.FLUSH, SeekType.SET, newPosition, SeekType.NONE,
            -1);

        if (!playBin.sendEvent(step)) {
            stopTest();
            logger.error("Error during step back");
        } else {
            logger.trace("Step back was succesfull");
        }
    };


    @Override
    public void TestAction() {
        logger.info(videoInfo.toString());
    }


    @Override
    public void playFrameFront(int count) {
        logger.debug("Play " + count + " frame/s forward");

        long start = getBuffer().getPresentationTimestamp().toNanos();
        long frame = videoInfo.getNumberOfFrame(start);
        long stop = videoInfo.getPositionOfFrame(frame + count);
        SeekEvent event = new SeekEvent(1.0, Format.TIME, SeekFlags.FLUSH, SeekType.SET, start, SeekType.SET, stop);
        if (!playBin.sendEvent(event)) {
            stopTest();
            logger.error("Error during play " + count + " frame/s");
        }
        playBin.play();

        while (playBin.isPlaying()) {
            // wait for EOS
            // I know that this is really UGLY
        }
        normalizeWayToPlay(stop, videoInfo.getVideoEnd());

    }


    @Override
    public void playFrameBack(int count) {
        logger.debug("Play " + count + " frame/s back");

        long start = getBuffer().getPresentationTimestamp().toNanos();
        long frame = videoInfo.getNumberOfFrame(start);
        long stop = videoInfo.getPositionOfFrame(frame - count > 0 ? frame - count : 0);

        SeekEvent step = new SeekEvent(-1.0, Format.TIME, SeekFlags.FLUSH, SeekType.SET, stop, SeekType.SET, start);
        if (!playBin.sendEvent(step)) {
            stopTest();
            logger.error("Error during play " + count + " frame/s");
        }
        playBin.play();

        while (playBin.isPlaying()) {
            // wait for EOS
            // I know that this is really UGLY
        }
        normalizeWayToPlay(stop, videoInfo.getVideoEnd());
    }


    @Override
    public void seek(long number) {
        logger.info("Seek to '" + number + "'");
        SeekEvent seek = new SeekEvent(1.0, Format.TIME, SeekFlags.FLUSH, SeekType.SET, number, SeekType.NONE, -1);
        if (playBin.sendEvent(seek)) {
            logger.debug("Seek to '" + number + "' was successful");
        } else {
            logger.warn("Seek to '" + number + "' was unsuccessful");
        }
    }


    @Override
    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;

    }


    /////////////////////////////////////////////////// helper //////////////////////////////////////////////////
    /////////////////////////////////////////////////// methods//////////////////////////////////////////////////
    /**
     * Executor shutdown
     */
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


    /**
     * Returns Sample of current frame
     * 
     * @return
     */
    private Sample getSample() {
        Sample sample = null;
        if (!videoComponent.getAppSink().isEOS()) {
            if (!playBin.getState().equals(State.PLAYING)) {
                sample = videoComponent.getAppSink().pullPreroll();
            }
        } else {
            logger.error("EOS exception");
        }
        return sample;
    }


    /**
     * Return buffer of current frame
     * 
     * @return
     */
    private Buffer getBuffer() {
        return getSample().getBuffer();
    }


    /**
     * 
     * @param startSegment
     * @param stopSegment
     * @return
     */
    private boolean normalizeWayToPlay(long startSegment, long stopSegment) {
        SeekEvent step = new SeekEvent(1.0, Format.TIME, SeekFlags.FLUSH, SeekType.SET, startSegment, SeekType.SET,
            stopSegment);
        if (!playBin.sendEvent(step)) {
            stopTest();
            logger.error("Error during step back");
            return false;
        }
        return true;
    }

}
