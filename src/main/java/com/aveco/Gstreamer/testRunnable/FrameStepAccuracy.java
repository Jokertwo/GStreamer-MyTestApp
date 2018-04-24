package com.aveco.Gstreamer.testRunnable;

import java.awt.Color;
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
        
        while (isRunnig()) {
            sleep(200);
            gettCtrl().stepForward(1);
            sleep(200);
            saveImage(FRAME_STEP, FrameStepAccuracy.class.getName(), getPanel(), getValues());
        }
        getPlayBin().pause();
        logger.info("End of test 'FrameAccuracy'");

    }
    
    
    private List<Pair> getValues() {
        List<Pair> values = new LinkedList<Pair>();
        values.add(new Pair(gettCtrl().getActualFrame(), Color.BLACK));
        values.add(new Pair(gettCtrl().presentationTimeStemp().toString(),Color.BLACK));
        return values;
    }

}
