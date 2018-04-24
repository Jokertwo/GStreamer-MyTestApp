package com.aveco.Gstreamer.testRunnable;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.ITestControler;


public class HMSFAccuracy extends AbstractTest {

    private static final String FOLDER_NAME = "HMSFAccuracy";

    public static final Logger logger = LoggerFactory.getLogger(HMSFAccuracy.class);


    public HMSFAccuracy(ITestControler tCtrl, PlayBin playBin, JComponent panel) {
        super(tCtrl, playBin, panel);

    }


    @Override
    public void run() {
        logger.info("Start of test 'HMFSAccuracy'");
        Thread.currentThread().setName("Test-HMFSAccuracy");
        prepare(FOLDER_NAME, logger);
        getPlayBin().play();
        while (getPlayBin().isPlaying() && isRunnig()) {
            getPlayBin().pause();
            logger.info("Video was paused");
            sleep(500);
            saveImage(FOLDER_NAME, HMSFAccuracy.class.getName(), getPanel(), getValues());
            getPlayBin().play();
            logger.info("Play video");
            sleep(500);
        }
        getPlayBin().pause();
        logger.info("End of test 'FrameAccuracy'");
    }


    private List<Pair> getValues() {
        List<Pair> values = new LinkedList<Pair>();
        values.add(new Pair(gettCtrl().frameRate(), Color.BLACK));
        values.add(new Pair(gettCtrl().timeForOneFrame(), Color.BLACK));
        values.add(new Pair(gettCtrl().timeCode(), Color.RED));
        values.add(new Pair(gettCtrl().presentationTimeStemp(), Color.BLACK));
        values.add(new Pair(gettCtrl().getActualFrame(), Color.BLACK));
        values.add(new Pair(gettCtrl().actualTimeT(), Color.BLACK));

        return values;
    }

}
