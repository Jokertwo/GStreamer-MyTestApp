package com.aveco.Gstreamer.ctrl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.freedesktop.gstreamer.Buffer;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.SeekFlags;
import org.freedesktop.gstreamer.SeekType;
import org.freedesktop.gstreamer.State;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.event.SeekEvent;
import org.freedesktop.gstreamer.event.StepEvent;
import org.freedesktop.gstreamer.examples.SimpleVideoComponent;
import org.freedesktop.gstreamer.query.SeekingQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.gui.IMyGVideoPlayer;
import com.aveco.Gstreamer.testRunnable.AbstractTest;
import com.aveco.Gstreamer.testRunnable.FrameStepAccuracy;
import com.aveco.Gstreamer.testRunnable.HMSFAccuracy;


public class TestControler implements ITestControler {

    public static final Logger logger = LoggerFactory.getLogger(TestControler.class);

    private PlayBin playBin;
    private SimpleVideoComponent vCmp;
    private ExecutorService executor;
    private List<AbstractTest> tests;
    private SeekingQuery q;


    public TestControler(IMyGVideoPlayer videoPlayer) {
        super();
        this.playBin = videoPlayer.getPlayBin();
        this.vCmp = videoPlayer.getSimpleVideoCompoment();
        executor = Executors.newSingleThreadExecutor();
        q = new SeekingQuery(Format.TIME);
        tests = new ArrayList<>();
    }


    @Override
    public Sample getSample() {
        Sample sample;
        if (!playBin.getState().equals(State.PLAYING)) {
            sample = vCmp.getAppSink().pullPreroll();
        } else {
            sample = null;
//            sample = vCmp.getAppSink().getLastBuffer();
        }
        return sample;
    }


    @Override
    public Buffer getBuffer() {
        return getSample().getBuffer();
    }


    @Override
    public String timeCode() {
        Buffer buf = getBuffer();
        long time = buf.getPresentationTimestamp().toNanos();
        long actualFrame = actualFrame(time, buf.getDuration().toNanos());
        int frameI = frameInSec(actualFrame);

        String frame = frameI < 10 ? "0" + frameI : String.valueOf(frameI);
        frame = "Time Code: " + buf.getPresentationTimestamp() + ":"
                + frame;
        logger.debug(frame);
        return frame;
    }


    @Override
    public String frameRate() {
        String frameRate = "Frame Rate: " + getFrameRate();
        logger.debug(frameRate);
        return frameRate;
    }


    @Override
    public String timeForOneFrame() {
        String timeForOneFrame = "Time for 1 frame: " + getBuffer().getDuration().toNanos();
        logger.debug(timeForOneFrame);
        return timeForOneFrame;
    }


    @Override
    public String getDuration() {
        logger.warn("Uninplemented!!!");
//        String durationS = "Frame Duration: " + getBuffer().getDuration().toNanos();
//        logger.debug(durationS);
        return "";
    }


    @Override
    public String getActualFrame() {
        Buffer buf = getBuffer();
        String actualFrame = "Actual frame: "
                + actualFrame(buf.getPresentationTimestamp().toNanos(), buf.getDuration().toNanos());
        logger.debug(actualFrame);
        return actualFrame;
    }


    public int frameInSec(long frames) {
        int result = (int) (frames % getFrameRate());
        return result;
    }


    public long actualFrame(long position, long timeForOneFrame) {
        return position / timeForOneFrame;
    }


    public double getFrameRate() {
        Sample sample = vCmp.getAppSink().pullPreroll();
        return sample.getCaps().getStructure(0).getFraction("framerate").toDouble();
    }


    @Override
    public String actualTimeT() {
        logger.debug("Query Position(TIME):" + playBin.queryPosition(Format.TIME));
        return "Query Position(TIME):" + playBin.queryPosition(Format.TIME);
    }


    @Override
    public String presentationTimeStemp() {
        String timeStamp = "TimeStamp: " + getBuffer().getPresentationTimestamp().toNanos();
        logger.debug(timeStamp);
        return timeStamp;
    }


    @Override
    public String queryDuration() {
        return "Query Duration: " + playBin.queryDuration(Format.TIME);
    }


    @Override
    public void runTests() {
        logger.trace("Add tests to executor");
        HMSFAccuracy hmsfAcc = new HMSFAccuracy(this, playBin, vCmp);
        FrameStepAccuracy frameAcc = new FrameStepAccuracy(this, playBin, vCmp);
        tests.add(frameAcc);
        executor.execute(frameAcc);
    }


    @Override
    public void shotDown() {
        executor.shutdown();
    }


    @Override
    public void stopTest() {
        logger.info("All test will be ends");

        Iterator<AbstractTest> iter = tests.iterator();

        while (iter.hasNext()) {
            AbstractTest item = iter.next();
            item.stopTest();
            iter.remove();
        }

    }


    @Override
    public long getVideoEnd() {
        if (playBin.query(q)) {
            return q.getEnd();
        }
        return 0;
    }


    @Override
    public void stepForward(int count) {
        logger.debug("Step " + count + " frame/s forward");
        if (!playBin
            .sendEvent(new StepEvent(Format.TIME, getBuffer().getDuration().toNanos() + 1, count, true, false))) {
            stopTest();
            logger.error("Error during step forward");
        }

    }


    @Override
    public void stepBack(int count) {
        logger.debug("Step " + count + " frame/s back");
        long start = getBuffer().getPresentationTimestamp().toNanos() - (getBuffer().getDuration().toNanos() * count);
        if (!playBin.sendEvent(new SeekEvent(-1.0, Format.TIME, SeekFlags.FLUSH, SeekType.NONE, -1, SeekType.SET,
            start))) {
            stopTest();
            logger.error("Error during step back");
        }
    }


    @Override
    public void currentPosition() {
        logger.debug("Update current position.");
        if (!playBin.seek(getBuffer().getPresentationTimestamp().toNanos(), TimeUnit.NANOSECONDS)) {
            logger.error("Error during update current position");
        }

    }

}
