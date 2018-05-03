package com.aveco.Gstreamer.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Proccesing extends JPanel {

    private static final String PROCCES = "Processing...";


    public Proccesing() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        JLabel label = new JLabel(PROCCES);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        add(label, BorderLayout.CENTER);
    }
}
