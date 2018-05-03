package com.aveco.Gstreamer.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
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

            buttonPanel = new ButtonPanel(fileChooser(), ctrlVideo);

            rootPanel = new JPanel(cl);

            JPanel video = new JPanel(new BorderLayout());
            video.add(videoPlayer.getVideoCompoment(), BorderLayout.CENTER);

            rootPanel.add(video, videoS);
            rootPanel.add(new Proccesing(), proccesS);
            cl.show(rootPanel, videoS);

            new MyGWindow(rootPanel, logInfo, new CommandTextField(commandBuffer), buttonPanel);
        });
    }


    private JButton fileChooser() {
        JButton fileChooserBtn = new JButton("File");
        fileChooserBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                timer();
                procces.preprocess(fileChooser.getSelectedFile().toURI());
            }
        });
        return fileChooserBtn;
    }


    private void timer() {
        Timer t = new Timer(5000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(rootPanel, videoS);
            }
        });
        t.setRepeats(false);
        t.start();
        cl.show(rootPanel, proccesS);
    }
}
