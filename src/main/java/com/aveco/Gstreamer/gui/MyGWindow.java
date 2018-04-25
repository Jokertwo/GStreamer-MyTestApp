package com.aveco.Gstreamer.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class MyGWindow extends JFrame {

    public MyGWindow(JPanel videoPlayer, JPanel logPanel, JTextField field) {
        setLayout(new MigLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("GWindow");
        add(videoPlayer, "pushx,growx,h 50%,wrap");
        add(logPanel, "pushx,growx,h 50%,wrap");
        add(field, "pushx,growx");
        setPreferredSize(new Dimension(600, 500));
        pack();
        setVisible(true);
        setAlwaysOnTop(true);
        field.grabFocus();
    }

}
