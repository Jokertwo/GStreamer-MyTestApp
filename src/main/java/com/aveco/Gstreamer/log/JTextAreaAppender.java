package com.aveco.Gstreamer.log;

import javax.swing.JTextArea;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;


public class JTextAreaAppender extends ConsoleAppender<ILoggingEvent> {

    private static JTextArea textArea;


    public JTextArea getTextArea() {
        return textArea;
    }


    public static void setTextArea(JTextArea textArea) {

        JTextAreaAppender.textArea = textArea;
    }
    

    @Override
    protected void append(final ILoggingEvent eventObject) {

        final JTextArea ta = getTextArea();
        if (ta != null && eventObject != null) {
            ta.append(eventObject.toString());           
            ta.append("\n");
            ta.setCaretPosition(ta.getDocument().getLength());
        }

    }
    
    

}

