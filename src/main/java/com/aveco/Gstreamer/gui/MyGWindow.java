package com.aveco.Gstreamer.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class MyGWindow extends JFrame {

    public MyGWindow(JPanel videoPlayer, JPanel logPanel) {
        setLayout(new MigLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("GWindow");
        add(videoPlayer, "pushx,growx,h 50%,wrap");
        add(logPanel, "pushx,growx,h 50%");
        setPreferredSize(new Dimension(500, 400));
        pack();
        setVisible(true);
        setAlwaysOnTop(true);
    }

}
