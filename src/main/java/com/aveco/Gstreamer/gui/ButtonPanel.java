package com.aveco.Gstreamer.gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.aveco.Gstreamer.action.AbstractCtrlAction;
import com.aveco.Gstreamer.action.End;
import com.aveco.Gstreamer.action.Exit;
import com.aveco.Gstreamer.action.Pause;
import com.aveco.Gstreamer.action.Play;
import com.aveco.Gstreamer.action.RewindBack;
import com.aveco.Gstreamer.action.RewindFront;
import com.aveco.Gstreamer.action.Start;
import com.aveco.Gstreamer.action.StepBack;
import com.aveco.Gstreamer.action.StepForward;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;
import net.miginfocom.swing.MigLayout;


@SuppressWarnings("serial")
public class ButtonPanel extends JPanel {

    private static final String migC = "growx,pushx";
    private List<JButton> buttons;


    public ButtonPanel(JButton fileChooser, VideoPlayerCtrl videoCtrl) {
        buttons = new ArrayList<>();
        setLayout(new MigLayout());
        add(fileChooser, migC);
        add(createButton(new Start(videoCtrl)), migC);
        add(createButton(new RewindBack(videoCtrl)), migC);
        add(createButton(new StepBack(videoCtrl)), migC);
        add(createButton(new Play(videoCtrl)), migC);
        add(createButton(new Pause(videoCtrl)), migC);
        add(createButton(new StepForward(videoCtrl)), migC);
        add(createButton(new RewindFront(videoCtrl)), migC);
        add(createButton(new End(videoCtrl)), migC);
        add(createButton(new Exit(videoCtrl)), migC);
    }


    private JButton createButton(AbstractCtrlAction action) {
        JButton btn = new JButton(action);
        btn.setToolTipText(action.help());
        buttons.add(btn);
        return btn;
    }


    public void setEnableBtn(boolean enable) {
        for (JButton item : buttons) {
            item.setEnabled(enable);
        }
    }

}
