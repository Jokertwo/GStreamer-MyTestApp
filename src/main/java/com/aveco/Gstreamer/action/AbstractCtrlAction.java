package com.aveco.Gstreamer.action;

import javax.swing.AbstractAction;
import org.slf4j.Logger;

@SuppressWarnings("serial")
public abstract class AbstractCtrlAction extends AbstractAction implements Action {

    
    public AbstractCtrlAction(String name) {
        super(name);
    }
    public AbstractCtrlAction() {
    }
        
    int getNumber(String argument, Logger logger) {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            logger.error("Cannot parse '" + argument + "' to number.");
        }
        return -1;
    }

}
