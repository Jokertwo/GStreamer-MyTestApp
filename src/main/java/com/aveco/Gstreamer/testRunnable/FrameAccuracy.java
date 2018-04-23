package com.aveco.Gstreamer.testRunnable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import org.apache.commons.io.FileUtils;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.ITestControler;


public class FrameAccuracy extends AbstractTest {

    private ITestControler tCtrl;
    private PlayBin playBin;
    private JComponent panel;
    private int counter = 0;
    private int yPosition = 23;

    private static final String FOLDER_NAME = PATH + "frameAccuracy";

    public static final Logger logger = LoggerFactory.getLogger(FrameAccuracy.class);


    public FrameAccuracy(ITestControler tCtrl, PlayBin playBin, JComponent panel) {
        super();
        this.tCtrl = tCtrl;
        this.playBin = playBin;
        this.panel = panel;
    }


    @Override
    public void run() {
        logger.info("Start of test 'FrameAccuracy'");
        Thread.currentThread().setName("Test-image-saver");
        prepare();
        playBin.play();
        while (playBin.isPlaying() && isRunnig()) {
            playBin.pause();
            logger.info("Video was paused");
            sleep(500);
            saveImage(FOLDER_NAME, FrameAccuracy.class.getName(), panel, getValues());
            playBin.play();
            logger.info("Play video");
            sleep(500);
        }
        playBin.pause();
        logger.info("End of test 'FrameAccuracy'");
    }


    private List<Pair> getValues() {
        List<Pair> values = new LinkedList<Pair>();
        values.add(new Pair(tCtrl.frameRate(), Color.BLACK));
        values.add(new Pair(tCtrl.timeForOneFrame(), Color.BLACK));
        values.add(new Pair(tCtrl.timeCode(), Color.RED));
        values.add(new Pair(tCtrl.presentationTimeStemp(), Color.BLACK));
        values.add(new Pair(tCtrl.getActualFrame(), Color.BLACK));
        values.add(new Pair(tCtrl.actualTimeT(),Color.BLACK));

        return values;
    }


    private void prepare() {
        logger.info("Deleting the result of the previous test if exist and prepare new empty folder");
        try {
            if (new File(FOLDER_NAME).exists()) {
                FileUtils.deleteDirectory(new File(FOLDER_NAME));
                createFile();
            } else {
                createFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createFile() {
        new File(FOLDER_NAME).mkdirs();
    }
}
