package com.aveco.Gstreamer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.freedesktop.gstreamer.Gst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.ITestControler;
import com.aveco.Gstreamer.ctrl.TestControler;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrlImpl;
import com.aveco.Gstreamer.gui.GUI;
import com.aveco.Gstreamer.gui.LogInfo;
import com.aveco.Gstreamer.log.CreateLogger;
import com.aveco.Gstreamer.playBin.IVideoPlayer;
import com.aveco.Gstreamer.playBin.VideoPlayer2;
import com.aveco.Gstreamer.videoInfo.VideoInfo;
import com.aveco.Gstreamer.videoInfo.VideoInfoImpl;


public class Main {
    private static final String[] PATHS = new String[10];
    static {
        PATHS[0] = "\\com\\aveco\\res\\a.mp4";
        PATHS[1] = "\\com\\aveco\\res\\b.mp4";
        PATHS[2] = "\\com\\aveco\\res\\c.mp4";
        PATHS[3] = "\\com\\aveco\\res\\tc_25fps_01min.avi";
        PATHS[4] = "\\com\\aveco\\res\\tc_25fps_01min.m2v";
        PATHS[5] = "\\com\\aveco\\res\\tc_29.97fps_df_1min.avi";
        PATHS[6] = "\\com\\aveco\\res\\tc_29.97fps_df_1min.mov";
        PATHS[7] = "\\com\\aveco\\res\\tc_30fps_ndf_01min.avi";
        PATHS[8] = "\\com\\aveco\\res\\tc_30fps_ndf_01min.m2v";
        PATHS[9] = "\\com\\aveco\\res\\tc_29.97fps_df_20min.avi";

    }

    public static final String PATH = PATHS[6];

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private LogInfo logInfo;
    private IVideoPlayer videoPlayer;
    private VideoInfo videoInfo;
    private ExecutorService executor;
    private VideoPlayerCtrl ctrlVideo;
    private CommandBuffer commandBuffer;
    private CommandLine commandLine;
    private ITestControler testControler;
    private VideoProcess videoProcces;


    public Main(String[] args) {
        new CreateLogger();
        SwingUtilities.invokeLater(() -> logInfo = new LogInfo());

        logger.info("Begin of app for testing video.");

        logger.trace("Inicialize of Gsreamer");
        args = Gst.init("FirstAppGst", args);
        executor = Executors.newFixedThreadPool(2);

        commandBuffer = new CommandBuffer();
        videoInfo = new VideoInfoImpl();
        videoPlayer = new VideoPlayer2();

        testControler = new TestControler(videoPlayer, videoInfo);
        ctrlVideo = new VideoPlayerCtrlImpl(videoPlayer, testControler, videoInfo, executor, commandBuffer);
        commandLine = new CommandLine(ctrlVideo, commandBuffer);

        executor.execute(commandLine);
        videoProcces = new VideoProcess(executor, videoPlayer, testControler, ctrlVideo);

        // start gui
        new GUI(videoPlayer, commandBuffer, ctrlVideo, logInfo, videoProcces);

    }


    public static void main(String[] args) {

        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
        new Main(args);
    }

}
