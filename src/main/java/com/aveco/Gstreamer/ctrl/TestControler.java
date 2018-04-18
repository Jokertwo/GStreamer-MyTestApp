package com.aveco.Gstreamer.ctrl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.freedesktop.gstreamer.ClockTime;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.examples.SimpleVideoComponent;
import org.freedesktop.gstreamer.query.SeekingQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.gui.IMyGVideoPlayer;
import com.aveco.Gstreamer.testRunnable.AbstractTest;
import com.aveco.Gstreamer.testRunnable.FrameAccuracy;


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
    public String timeCode() {
        int frameI = getActualFrame(playBin.queryPosition(Format.TIME), getFrameRate());
        String frame = frameI < 10 ? "0" + frameI : String.valueOf(frameI);
        frame = "Time Code: " + ClockTime.fromNanos(playBin.queryPosition(Format.TIME)).toString() + ":"
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
        String timeForOneFrame = "Time for 1 frame: " + timeForOneFrame(getFrameRate());
        logger.debug(timeForOneFrame);
        return timeForOneFrame;
    }


    @Override
    public String getDuration() {
        long duration = playBin.queryDuration(Format.TIME);
        String durationS = "Video Duration: " + ClockTime.fromNanos(duration).toString() + ":"
                + getActualFrame(duration, getFrameRate());
        logger.debug(durationS);
        return durationS;
    }


    @Override
    public String getActualFrame() {
        String actualFrame = "Actual frame: " + getActualFrame(playBin.queryDuration(Format.TIME), getFrameRate());
        logger.debug(actualFrame);
        return actualFrame;
    }


    public double timeForOneFrame(double frameRate) {
        logger.trace("Time for one frame: " + (1000000000 / frameRate));
        return 1000000000 / frameRate;
    }


    public int getActualFrame(long playBinPosition, double frameRate) {
        double temp = playBinPosition / timeForOneFrame(frameRate);
        temp = temp % frameRate;
        return (int) temp;
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
    public String actualTimeP() {
        return "Query Position(Percent):" + playBin.queryPosition(Format.PERCENT);
    }


    @Override
    public String queryDuration() {
        return "Query Duration: " + playBin.queryDuration(Format.TIME);
    }


    @Override
    public void runTests() {
        logger.trace("Add tests to executor");
        FrameAccuracy frameAcc = new FrameAccuracy(this, playBin, vCmp);
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

}
