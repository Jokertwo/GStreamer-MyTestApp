package com.aveco.Gstreamer.testRunnable;

import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.ITestControler;


public class FrameStepAccuracy extends AbstractTest {

    private static final String FRAME_STEP = "frameStepTest";
    public static final Logger logger = LoggerFactory.getLogger(FrameStepAccuracy.class);


    public FrameStepAccuracy(ITestControler tCtrl, PlayBin playBin, JComponent panel) {
        super(tCtrl, playBin, panel);
    }


    @Override
    public void run() {
        logger.info("Start of test 'FrameStepAccuracy'");
        Thread.currentThread().setName("Test-FrameStepAccuracy");
        prepare(FRAME_STEP, logger);

        getPlayBin().pause();
        logger.info("Video was paused");
        sleep(200);
        saveImage(FRAME_STEP, FrameStepAccuracy.class.getName(), getPanel(), getValues());
        sleep(200);
        for (int i = 0; i < 100; i++) {

            getCtrl().getVideoPlayerCtrl().stepForward(5);
            sleep(200);
            getCtrl().getVideoPlayerCtrl().stepForward(5);
            sleep(200);
            getCtrl().getVideoPlayerCtrl().stepForward(5);
            sleep(200);
            getCtrl().getVideoPlayerCtrl().stepBack(5);
            sleep(200);
            getCtrl().getVideoPlayerCtrl().stepBack(5);
            sleep(200);
            getCtrl().getVideoPlayerCtrl().stepBack(5);
            sleep(200);
        }
        saveImage(FRAME_STEP, FrameStepAccuracy.class.getName(), getPanel(), getValues());
        getPlayBin().pause();
        logger.info("End of test 'FrameAccuracy'");

    }


    private List<Pair> getValues() {
        List<Pair> values = new LinkedList<Pair>();

        return values;
    }

}
