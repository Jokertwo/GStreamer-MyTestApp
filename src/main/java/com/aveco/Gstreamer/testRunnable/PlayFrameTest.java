package com.aveco.Gstreamer.testRunnable;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.ITestControler;


public class PlayFrameTest extends AbstractTest {

    private static final String PLAY_FRAME = "PlayFrameTest";
    public static final Logger logger = LoggerFactory.getLogger(SteppingFrontBack.class);

    public PlayFrameTest(ITestControler tCtrl, PlayBin playBin, JComponent panel) {
        super(tCtrl, playBin, panel);
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Play-Frame-Test");
        getPlayBin().play();
        sleep(5000);
        getPlayBin().pause();
        sleep(200);
        prepare(PLAY_FRAME, logger);
        for (int i = 0; i < 10; i++) {

            sleep(200);
            saveImage(PLAY_FRAME, SteppingFrontBack.class.getName(), getPanel(), getValues(i));
            for (int j = 0; j < 50; j++) {
                sleep(200);
                gettCtrl().playFrameForward(1);
                if (!isRunnig()) {
                    break;
                }
            }
            for (int j = 0; j < 50; j++) {
                sleep(200);
                gettCtrl().stepBack(1);
                if (!isRunnig()) {
                    break;
                }
            }
            if (!isRunnig()) {
                break;
            }
            saveImage(PLAY_FRAME, SteppingFrontBack.class.getName(), getPanel(), getValues(i));
        }

    }


    private List<Pair> getValues(int value) {
        List<Pair> values = new LinkedList<Pair>();
        values.add(new Pair("Iteration number " + value, Color.BLACK));
        values.add(new Pair(gettCtrl().presentationTimeStemp().toString(), Color.BLACK));
        return values;
    }

}
