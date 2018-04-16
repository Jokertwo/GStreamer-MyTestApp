package com.aveco.Gstreamer.ctrl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JPanel;
import org.freedesktop.gstreamer.ClockTime;
import org.freedesktop.gstreamer.Format;
import org.freedesktop.gstreamer.Sample;
import org.freedesktop.gstreamer.elements.PlayBin;
import org.freedesktop.gstreamer.examples.SimpleVideoComponent;
import com.aveco.Gstreamer.gui.IMyGVideoPlayer;
import com.aveco.Gstreamer.testRunnable.FrameAccuracy;


public class TestControler implements ITestControler {

    private PlayBin playBin;
    private SimpleVideoComponent vCmp;
    ExecutorService executor = Executors.newSingleThreadExecutor();
   

    public TestControler(IMyGVideoPlayer videoPlayer) {
        super();
        this.playBin = videoPlayer.getPlayBin();
        this.vCmp = videoPlayer.getSimpleVideoCompoment();
    }


    @Override
    public String timeCode() {
        int frameI = getActualFrame(playBin.queryPosition(Format.TIME), getFrameRate());
        String frame = frameI < 10 ? "0" + frameI : String.valueOf(frameI);
        return "Time Code: " + ClockTime.fromNanos(playBin.queryPosition(Format.TIME)).toString() + ":"
                + frame;
    }


    @Override
    public String frameRate() {
        return "Frame Rate: " + getFrameRate();
    }


    @Override
    public String timeForOneFrame() {
        return "Time for 1 frame: " + timeForOneFrame(getFrameRate());
    }


    @Override
    public String getDuration() {
        long duration = playBin.queryDuration(Format.TIME);
        return "Video Duration: " + ClockTime.fromNanos(duration).toString() + ":"
                + getActualFrame(duration, getFrameRate());
    }


    @Override
    public String getActualFrame() {
        return "Actual frame: " + getActualFrame(playBin.queryDuration(Format.TIME), getFrameRate());
    }


    public double timeForOneFrame(double frameRate) {
        return 1000000000 / frameRate;
    }


    public int getActualFrame(long playBinDuration, double frameRate) {
        return (int) (playBinDuration / timeForOneFrame(frameRate) % frameRate);
    }


    public double getFrameRate() {
        Sample sample = vCmp.getAppSink().pullPreroll();
        return sample.getCaps().getStructure(0).getFraction("framerate").toDouble();
    }


    public String actualTimeT() {
        return "Query Position(TIME):" + playBin.queryPosition(Format.TIME);
    }


    public String actualTimeP() {
        return "Query Position(Percent):" + playBin.queryPosition(Format.PERCENT);
    }


    @Override
    public void runTest() {
        executor.execute(new FrameAccuracy(this, playBin, vCmp));
    }
    
    public void shotDown(){
        executor.shutdown();
    }

}
