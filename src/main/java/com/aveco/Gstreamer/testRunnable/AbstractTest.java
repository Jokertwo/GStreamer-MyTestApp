package com.aveco.Gstreamer.testRunnable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

public abstract class AbstractTest implements Runnable {

    public static final String PATH = "testResolut/";
    private boolean run = true;
    private int yPosition = 23;

    private Object lock = new Object();
    private int counter = 0;

    public void stopTest() {
        synchronized (lock) {
            run = false;
        }
    }
    
    public void saveImage(String folderName,String testName,JComponent panel,List<Pair> values){
        int i = 1;
        BufferedImage bi = new BufferedImage(panel.getSize().width * 2 + 50, panel.getSize().height,
            BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        panel.paintComponents(g);
        
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        for(Pair item : values){
            g.setColor(item.getColor());
            g.drawString(item.getValue(), bi.getWidth() / 2 + 10, yPosition * i);
            i++;
        }
        g.dispose();
        
        try {
            ImageIO.write(bi, "png", new File(folderName + "/" + testName + String.valueOf(counter) + ".png"));
            counter++;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
