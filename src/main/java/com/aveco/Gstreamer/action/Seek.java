package com.aveco.Gstreamer.action;

import java.awt.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.VideoPlayerCtrl;


public class Seek extends AbstractCtrlAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private VideoPlayerCtrl ctrl;
    public static final Logger logger = LoggerFactory.getLogger(Seek.class);


    public Seek(VideoPlayerCtrl ctrl) {
        super();
        this.ctrl = ctrl;
    }


    @Override
    public void doIt(String[] argument) {
        if (argument.length > 1) {
            long number;
            try {
                number = Long.parseLong(argument[1]);
                ctrl.seek(number);
            } catch (NumberFormatException e) {
                logger.error("Cannot parse '" + argument[1] + "' to long!");
            }

        } else {
            logger.error("Canot seek when i have not position, add argument{number}");
        }

    }


    @Override
    public String help() {
        // TODO Auto-generated method stub
        return "Seek to position in agrument";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.err.println("Unimplemented!!!");

    }

}
