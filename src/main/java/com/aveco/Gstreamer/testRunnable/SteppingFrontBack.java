package com.aveco.Gstreamer.testRunnable;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.ITestControler;


public class SteppingFrontBack extends AbstractTest {

    public static final Logger logger = LoggerFactory.getLogger(SteppingFrontBack.class);

    private static final String FRAME_STEP = "steppingTest";


    public SteppingFrontBack(ITestControler tCtrl, PlayBin playBin, JComponent panel) {
        super(tCtrl, playBin, panel); // TODO Auto-generated constructor stub
    }


    @Override
    public void run() {
        logger.info("Start of test 'SteppingFrontBack'");
        Thread.currentThread().setName("Test-SteppingFrontBacky");
        prepare(FRAME_STEP, logger);
        getPlayBin().play();
        sleep(10000);
        logger.info("Video was paused");
        getPlayBin().pause();

        for (int i = 0; i < 5; i++) {
            sleep(200);
            saveImage(FRAME_STEP, SteppingFrontBack.class.getName(), getPanel(), getValues(i));
            for (int j = 0; j < 50; j++) {
                sleep(200);
                getCtrl().getVideoPlayerCtrl().stepForward(1);
                if (!isRunnig()) {
                    break;
                }
            }
            for (int j = 0; j < 50; j++) {
                sleep(200);
                getCtrl().getVideoPlayerCtrl().stepBack(1);
                if (!isRunnig()) {
                    break;
                }
            }
            sleep(200);
            saveImage(FRAME_STEP, SteppingFrontBack.class.getName(), getPanel(), getValues(i));
            if (!isRunnig()) {
                break;
            }
        }

//            sleep(200);
//            saveImage(FRAME_STEP, SteppingFrontBack.class.getName(), getPanel(), getValues());

        getPlayBin().pause();
        logger.info("End of test 'SteppingFrontBack'");

    }


    private List<Pair> getValues(int value) {
        List<Pair> values = new LinkedList<Pair>();
        values.add(new Pair("Iteration number " + value, Color.BLACK));
        return values;
    }

}
