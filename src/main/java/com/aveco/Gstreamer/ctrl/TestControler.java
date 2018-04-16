package com.aveco.Gstreamer.ctrl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.freedesktop.gstreamer.ClockTime;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.examples.SimpleVideoComponent;
import com.aveco.Gstreamer.gui.IMyGVideoPlayer;


public class TestControler implements ITestControler {

    private PlayBin playBin;
    private SimpleVideoComponent vCmp;
    private JPanel panel;

    private int yPosition = 23;
    private int width = 936;

    private int counter = 0;


    public TestControler(IMyGVideoPlayer videoPlayer) {
        super();
        this.playBin = videoPlayer.getPlayBin();
        this.vCmp = videoPlayer.getSimpleVideoCompoment();
        this.panel = videoPlayer.getPanel();
    }


    @Override
    public String timeCode() {
        int frameI = getActualFrame();
        String frame = frameI < 10 ? "0" + frameI : String.valueOf(frameI);
        return "Time Code: " + ClockTime.fromNanos(playBin.queryPosition(Format.TIME)).toString() + ":"
                + frame;
    }


    @Override
    public String frameRate() {
        return "Frame Rate: " + getFrameRate();
    }


    private String actualTimeT() {
        return "Query Position(TIME):" + playBin.queryPosition(Format.TIME);
    }


    private String actualTimeP() {
        return "Query Position(Percent):" + playBin.queryPosition(Format.PERCENT);
    }


    @Override
    public String duration() {
        return "Video Duration: " + ClockTime.fromNanos(playBin.queryDuration(Format.TIME)).toString() + ":"
                + (int) (playBin.queryDuration(Format.TIME) / timeForOneFrame() % getFrameRate());
    }


    @Override
    public int getActualFrame() {
        return (int) (playBin.queryPosition(Format.TIME) / timeForOneFrame() % getFrameRate());
    }


    @Override
    public double timeForOneFrame() {
        return 1000000000 / getFrameRate();
    }


    @Override
    public double getFrameRate() {
        Sample sample = vCmp.getAppSink().pullPreroll();
        return sample.getCaps().getStructure(0).getFraction("framerate").toDouble();
    }


    @Override
    public void runTest() {
        new Thread(() -> {
            Thread.currentThread().setName("Test-image-saver");
            playBin.play();
            while (playBin.isPlaying()) {
                playBin.pause();
                sleep(500);
                saveImage();
                playBin.play();
                sleep(500);
            }
            System.out.println("End of test");
        }).start();
    }


    @Override
    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void saveImage() {
        BufferedImage bi = new BufferedImage(panel.getSize().width * 2, panel.getSize().height,
            BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        panel.paint(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        g.drawString(frameRate(), bi.getWidth() / 2 + 10, yPosition * 1);
        g.drawString(duration(), bi.getWidth() / 2 + 10, yPosition * 2);
        g.setColor(Color.RED);
        g.drawString(timeCode(), bi.getWidth() / 2 + 10, yPosition * 3);
        g.setColor(Color.BLACK);
        g.drawString(actualTimeT(), bi.getWidth() / 2 + 10, yPosition * 4);
        g.drawString(actualTimeP(), bi.getWidth() / 2 + 10, yPosition * 5);
        g.dispose();
        try {
            ImageIO.write(bi, "png", new File("imgTest/img" + String.valueOf(counter) + ".png"));
            counter++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
