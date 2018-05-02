package com.aveco.Gstreamer.log;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;


public class CreateLogger {
    
    
    public CreateLogger() {
        JTextAreaAppender appender = new JTextAreaAppender();
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LoggerContext loggerContext = rootLogger.getLoggerContext();
//        // we are not interested in auto-configuration
//        loggerContext.reset();

//        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
//        encoder.setContext(loggerContext);
//        encoder.setPattern("%date | %-5level | %logger[%thread] - %message%n");
//        encoder.start();
        
        appender.setContext(loggerContext);
//        appender.setEncoder(encoder);
        appender.start();

        rootLogger.addAppender(appender);

       
    }

}
