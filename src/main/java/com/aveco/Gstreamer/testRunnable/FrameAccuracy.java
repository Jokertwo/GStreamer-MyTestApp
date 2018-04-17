package com.aveco.Gstreamer.testRunnable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.freedesktop.gstreamer.elements.PlayBin;
import com.aveco.Gstreamer.ctrl.ITestControler;


public class FrameAccuracy extends AbstractTest {

    private ITestControler tCtrl;
    private PlayBin playBin;
    private JComponent panel;
    private int counter = 0;
    private int yPosition = 23;

    private static final String FOLDER_NAME = PATH + "frameAccuracy";

    public static final Logger logger = LogManager.getLogger();


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
            saveImage();
            playBin.play();
            logger.info("Play video");
            sleep(500);
        }
        playBin.pause();
        logger.info("End of test 'FrameAccuracy'");
        System.out.println("End of test 'FrameAccuracy'");
    }


    private void saveImage() {

        BufferedImage bi = new BufferedImage(panel.getSize().width * 2 + 50, panel.getSize().height,
            BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        panel.paintComponents(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        g.drawString(tCtrl.frameRate(), bi.getWidth() / 2 + 10, yPosition * 1);
        g.drawString(tCtrl.getDuration(), bi.getWidth() / 2 + 10, yPosition * 2);
        g.setColor(Color.RED);
        g.drawString(tCtrl.timeCode(), bi.getWidth() / 2 + 10, yPosition * 3);
        g.setColor(Color.BLACK);
        g.drawString(tCtrl.actualTimeT(), bi.getWidth() / 2 + 10, yPosition * 4);
        g.drawString(tCtrl.actualTimeP(), bi.getWidth() / 2 + 10, yPosition * 5);
        g.drawString(tCtrl.queryDuration(), bi.getWidth() / 2 + 10, yPosition * 6);
        g.dispose();
        try {
            ImageIO.write(bi, "png", new File(FOLDER_NAME + "/img" + String.valueOf(counter) + ".png"));
            logger.trace("Save image '" + "img" + String.valueOf(counter) + ".png" + "' with result");
            counter++;
        } catch (Exception e) {
            e.printStackTrace();
        }
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