package com.aveco.Gstreamer.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.aveco.Gstreamer.CommandBuffer;
import com.aveco.Gstreamer.VideoProcess;
import com.aveco.Gstreamer.ctrl.GuiCtrl;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;
import com.aveco.Gstreamer.videoInfo.VideoInfo;
import net.miginfocom.swing.MigLayout;


public class GUI implements GuiCtrl {

    private JComponent videoPlayer;
    private CommandBuffer commandBuffer;
    private VideoPlayerCtrl ctrlVideo;
    private LogInfo logInfo;
    private CardLayout cl;
    private ButtonPanel buttonPanel;
    private JPanel rootPanel;
    private VideoProcess procces;

    private JFrame window;
    private JProgressBar leftLevel;
    private JProgressBar rightLevel;

    public static Timer timer;

    private static final String videoS = "video";
    private static final String proccesS = "process";
    private VideoInfo videoInfo;


    public GUI(JComponent videoPlayer,
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


    /**
     * Create hole gui
     */
    public void createGui() {
        // start gui
        SwingUtilities.invokeLater(() -> {

            buttonPanel = new ButtonPanel(fileChooser(), ctrlVideo, commandBuffer);

            rootPanel = new JPanel(cl);

            JPanel video = new JPanel(new BorderLayout());
            video.add(videoPlayer, BorderLayout.CENTER);

            rootPanel.add(video, videoS);
            rootPanel.add(new Proccesing(), proccesS);
            cl.show(rootPanel, videoS);

            window = new MyGWindow(rootPanel, logInfo, new CommandTextField(commandBuffer), buttonPanel, slider(),
                soundLevel(),volume());
        });
    }


    private JPanel soundLevel() {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        leftLevel = new JProgressBar();
        panel.add(new JLabel("Left:"));
        panel.add(leftLevel, "pushx,growx,wrap");
        rightLevel = new JProgressBar();
        panel.add(new JLabel("Right:"));
        panel.add(rightLevel, "pushx,growx,wrap");
        return panel;
    }


    /**
     * Button with filechosser action
     * 
     * @return
     */
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
                videoInfo = procces.preprocess(fileChooser.getSelectedFile().toURI());
                videoInfo.resolveCodec();
                ctrlVideo.setVideoInfo(videoInfo);
            }
        });
        return fileChooserBtn;
    }


    private JSlider volume() {
        JSlider position = new JSlider(0, 1000);
        position.addChangeListener(e -> {
            if (position.getValueIsAdjusting()) {
                double relPos = position.getValue() / 1000.0;
                ctrlVideo.setVolume(relPos);
            }
        });
        position.setValue(100);
        return position;
    }


    private JSlider slider() {
        // position slider
        JSlider position = new JSlider(0, 1000);
        position.addChangeListener(e -> {
            if (position.getValueIsAdjusting()) {
                long dur = videoInfo.getVideoEnd();
                if (dur > 0) {
                    double relPos = position.getValue() / 1000.0;
                    ctrlVideo.seek((long) (relPos * dur));
//                    videoPlayer.getPlayBin().seek((long) (relPos * dur), TimeUnit.NANOSECONDS);
                }
            }
        });
        // sync slider position to video when not dragging
        timer = new Timer(50, e -> {
            if (!position.getValueIsAdjusting()) {
                long dur = videoInfo.getVideoEnd();
                long pos = ctrlVideo.getPostion();
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


    @Override
    public void updateVolume(double[] levels) {
        if (levels.length > 0) {
            // convert levels for display
            for (int i = 0; i < levels.length; i++) {
                levels[i] = Math.pow(10, levels[i] / 20);
            }
            if (levels.length >= 2) {
                leftLevel.setValue((int) Math.max(0, Math.min(levels[0] * 100, 100)));
                rightLevel.setValue((int) Math.max(0, Math.min(levels[1] * 100, 100)));
            } else {
                leftLevel.setValue((int) Math.max(0, Math.min(levels[0] * 100, 100)));
                rightLevel.setValue((int) Math.max(0, Math.min(levels[0] * 100, 100)));
            }
        } else {
            leftLevel.setValue(0);
            rightLevel.setValue(0);
        }

    }


    @Override
    public void updateButton() {
        // TODO Auto-generated method stub

    }
}
