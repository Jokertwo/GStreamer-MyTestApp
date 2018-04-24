package com.aveco.Gstreamer.testRunnable;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import org.apache.commons.io.FileUtils;
import org.freedesktop.gstreamer.elements.PlayBin;
import com.aveco.Gstreamer.ctrl.ITestControler;
import org.slf4j.Logger;


public abstract class AbstractTest implements Runnable {

    public static final String PATH = "testResolut/";
    private boolean run = true;
    private int yPosition = 23;

    private ITestControler tCtrl;
    private PlayBin playBin;
    private JComponent panel;


    public ITestControler gettCtrl() {
        return tCtrl;
    }


    public PlayBin getPlayBin() {
        return playBin;
    }


    public JComponent getPanel() {
        return panel;
    }


    public AbstractTest(ITestControler tCtrl, PlayBin playBin, JComponent panel) {
        super();
        this.tCtrl = tCtrl;
        this.playBin = playBin;
        this.panel = panel;
    }

    private Object lock = new Object();
    private int counter = 0;


    public void stopTest() {
        synchronized (lock) {
            run = false;
        }
    }


    public void saveImage(String folderName, String testName, JComponent panel, List<Pair> values) {
        int i = 1;
        
        BufferedImage bi = new BufferedImage(panel.getSize().width * 2 + 50, panel.getSize().height,
            BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        panel.paint(g);

        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        for (Pair item : values) {
            g.setColor(item.getColor());
            g.drawString(item.getValue(), bi.getWidth() / 2 + 10, yPosition * i);
            i++;
        }
        g.dispose();

        try {
            ImageIO.write(bi, "png", new File(PATH + folderName + "/" + testName + String.valueOf(counter) + ".png"));
            counter++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void prepare(String folder, Logger logger) {
        logger.info("Deleting the result of the previous test if exist and prepare new empty folder");
        try {
            if (new File(PATH + folder).exists()) {
                FileUtils.deleteDirectory(new File(PATH + folder));
                createFile(PATH + folder);
            } else {
                createFile(PATH + folder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createFile(String folder) {
        new File(folder).mkdirs();
    }


    public boolean isRunnig() {
        synchronized (lock) {
            return run;
        }
    }


    protected void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
