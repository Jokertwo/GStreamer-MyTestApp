package com.aveco.Gstreamer.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class MyGWindow extends JFrame {

    public MyGWindow(JPanel videoPlayer, JPanel logPanel, JTextField field,JPanel buttons,JSlider slider,JPanel soundPanel,JSlider volume) {
        setLayout(new MigLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("GWindow");
        add(videoPlayer, "push,grow,wrap");
        add(slider,"pushx,growx,wrap");
        add(buttons,"pushx,growx,wrap");
        add(soundPanel,"pushx,growx,split");
        add(volume,"pushx,growx,wrap");
//        add(logPanel, "pushx,growx,h 50%,wrap");
        add(field, "pushx,growx");
        setPreferredSize(new Dimension(600, 500));
        pack();
        setVisible(true);
        field.grabFocus();
    }

}
