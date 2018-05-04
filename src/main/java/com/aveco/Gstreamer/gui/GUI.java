package com.aveco.Gstreamer.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.VideoProcess;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;
import com.aveco.Gstreamer.playBin.IVideoPlayer;


public class GUI {

    private IVideoPlayer videoPlayer;
    private CommandBuffer commandBuffer;
    private VideoPlayerCtrl ctrlVideo;
    private LogInfo logInfo;
    private CardLayout cl;
    private ButtonPanel buttonPanel;
    private JPanel rootPanel;
    private VideoProcess procces;
    private JFrame window;

    public static Timer timer;

    private static final String videoS = "video";
    private static final String proccesS = "process";


    public GUI(IVideoPlayer videoPlayer,
               CommandBuffer commandBuffer,
               VideoPlayerCtrl ctrlVideo,
               LogInfo logInfo,
               VideoProcess procces) {
        super();
        this.videoPlayer = videoPlayer;
        this.commandBuffer = commandBuffer;
        this.ctrlVideo = ctrlVideo;
        this.logInfo = logInfo;
        this.procces = procces;
        cl = new CardLayout();

        createGui();
    }


    public void createGui() {
        // start gui
        SwingUtilities.invokeLater(() -> {

            buttonPanel = new ButtonPanel(fileChooser(), ctrlVideo, commandBuffer);

            rootPanel = new JPanel(cl);

            JPanel video = new JPanel(new BorderLayout());
            video.add(videoPlayer.getVideoCompoment(), BorderLayout.CENTER);

            rootPanel.add(video, videoS);
            rootPanel.add(new Proccesing(), proccesS);
            cl.show(rootPanel, videoS);

            window = new MyGWindow(rootPanel, logInfo, new CommandTextField(commandBuffer), buttonPanel, slider());
        });
    }


    private JButton fileChooser() {
        JButton fileChooserBtn = new JButton("File");
        fileChooserBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileFilter filter = new FileNameExtensionFilter("Only video", "mp4", "m2v", "avi", "mkv", "mov");
            fileChooser.addChoosableFileFilter(filter);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                timer();
                window.setTitle(fileChooser.getSelectedFile().getName());
                procces.preprocess(fileChooser.getSelectedFile().toURI());
            }
        });
        return fileChooserBtn;
    }


    private JSlider slider() {
        // position slider
        JSlider position = new JSlider(0, 1000);
        position.addChangeListener(e -> {
            if (position.getValueIsAdjusting()) {
                long dur = videoPlayer.getPlayBin().queryDuration(TimeUnit.NANOSECONDS);
                if (dur > 0) {
                    double relPos = position.getValue() / 1000.0;
                    videoPlayer.getPlayBin().seek((long) (relPos * dur), TimeUnit.NANOSECONDS);
                }
            }
        });
        // sync slider position to video when not dragging
        timer = new Timer(50, e -> {
            if (!position.getValueIsAdjusting()) {
                long dur = videoPlayer.getPlayBin().queryDuration(TimeUnit.NANOSECONDS);
                long pos = videoPlayer.getPlayBin().queryPosition(TimeUnit.NANOSECONDS);
                if (dur > 0) {
                    double relPos = (double) pos / dur;
                    position.setValue((int) (relPos * 1000));
                }
            }
        });
        timer.start();
        position.setValue(0);
        return position;
    }


    private void timer() {
        Timer t = new Timer(5000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(rootPanel, videoS);
                buttonPanel.setEnableBtn(true);
            }
        });
        t.setRepeats(false);
        t.start();
        cl.show(rootPanel, proccesS);
    }
}
