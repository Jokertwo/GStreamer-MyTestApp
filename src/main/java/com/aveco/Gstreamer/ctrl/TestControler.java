package com.aveco.Gstreamer.ctrl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.playBin.IVideoPlayer;
import com.aveco.Gstreamer.playBin.VideoComponent;
import com.aveco.Gstreamer.testRunnable.AbstractTest;
import com.aveco.Gstreamer.testRunnable.PlayFrameTest;


public class TestControler implements ITestControler {

    public static final Logger logger = LoggerFactory.getLogger(TestControler.class);

    private PlayBin playBin;
    private VideoComponent vCmp;
    private ExecutorService executor;
    private List<AbstractTest> tests;
    private VideoPlayerCtrl videoPlayerCtrl;


    public TestControler(IVideoPlayer videoPlayer) {
        super();
        this.playBin = videoPlayer.getPlayBin();
        this.vCmp = videoPlayer.getVideoCompoment();
        executor = Executors.newSingleThreadExecutor();
        tests = new ArrayList<>();
    }


    @Override
    public void runTests() {
        logger.trace("Add tests to executor");
//        HMSFAccuracy hmsfAcc = new HMSFAccuracy(this, playBin, vCmp);
//        FrameStepAccuracy frameAcc = new FrameStepAccuracy(this, playBin, vCmp);
//        AbstractTest stepping = new SteppingFrontBack(this, playBin, vCmp);
        AbstractTest stepping = new PlayFrameTest(this, playBin, vCmp);
        tests.add(stepping);
        executor.execute(stepping);
    }


    @Override
    public void shotDown() {
        shutDownExecutor();
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


    @Override
    public VideoPlayerCtrl getVideoPlayerCtrl() {
        return videoPlayerCtrl;
    }


    @Override
    public void setVideoPlayerCtrl(VideoPlayerCtrl videoPlayerCtrl) {
        this.videoPlayerCtrl = videoPlayerCtrl;
    }

}
