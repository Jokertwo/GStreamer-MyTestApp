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
import org.freedesktop.gstreamer.elements.PlayBin;
import com.aveco.Gstreamer.ctrl.ITestControler;


public class FrameAccuracy extends AbstractTest {

    private ITestControler tCtrl;
    private PlayBin playBin;
    private JComponent panel;
    private int counter = 0;
    private int yPosition = 23;
    private int width = 936;
    private static final String FOLDER_NAME = "frameAccuracy";


    public FrameAccuracy(ITestControler tCtrl, PlayBin playBin, JComponent panel) {
        super();
        this.tCtrl = tCtrl;
        this.playBin = playBin;
        this.panel = panel;
    }


    @Override
    public void run() {
        Thread.currentThread().setName("Test-image-saver");
        prepare();
        playBin.play();
        while (playBin.isPlaying()) {
            playBin.pause();
            sleep(500);
            saveImage();
            playBin.play();
            sleep(500);
        }
        System.out.println("End of test");
    }


    private void saveImage() {
        BufferedImage bi = new BufferedImage(panel.getSize().width * 2, panel.getSize().height,
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
        g.dispose();
        try {
            ImageIO.write(bi, "png", new File(FOLDER_NAME + "/img" + String.valueOf(counter) + ".png"));
            counter++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void prepare() {
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
