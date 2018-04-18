package com.aveco.Gstreamer;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;
import com.aveco.Gstreamer.ctrl.action.ActualFrame;
import com.aveco.Gstreamer.ctrl.action.BufferInfo;
import com.aveco.Gstreamer.ctrl.action.CtrlAction;
import com.aveco.Gstreamer.ctrl.action.End;
import com.aveco.Gstreamer.ctrl.action.Exit;
import com.aveco.Gstreamer.ctrl.action.FrameRate;
import com.aveco.Gstreamer.ctrl.action.MinusOneSec;
import com.aveco.Gstreamer.ctrl.action.Pause;
import com.aveco.Gstreamer.ctrl.action.Play;
import com.aveco.Gstreamer.ctrl.action.PlusOneSec;
import com.aveco.Gstreamer.ctrl.action.RunTest;
import com.aveco.Gstreamer.ctrl.action.Sleep;
import com.aveco.Gstreamer.ctrl.action.Start;
import com.aveco.Gstreamer.ctrl.action.State;
import com.aveco.Gstreamer.ctrl.action.StopTest;
import com.aveco.Gstreamer.ctrl.action.Time;
import com.aveco.Gstreamer.ctrl.action.TimeCode;


public class CommandLine implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLine.class);

    private final String help = "man";
    private Map<String, CtrlAction> actions;
    private Scanner sc = new Scanner(System.in);


    public CommandLine(IVideoPlayerCtrl ctrl) {
        super();
        initActions(ctrl);
    }


    @Override
    public void run() {
        logger.info("Thread for command line begin run");
        Thread.currentThread().setName("Command-console");
        String commandLine;
        while (true) {
            commandLine = sc.nextLine().trim();
            // presed enter
            if (commandLine.isEmpty()) {
                continue;
            }
            String commands[] = commandLine.split(" ");

            for (String command : commands) {
                // print help
                if (command.equals(help)) {
                    printHelp();
                    continue;
                }
                // check command
                if (actions.containsKey(command)) {
                    actions.get(command).doIt();
                } else {
                    unknownCoomand(command);
                }
            }

        }

    }


    private void printHelp() {
        for (Map.Entry<String, CtrlAction> entry : actions.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue().help());
        }
    }


    private void unknownCoomand(String command) {
        logger.trace("Unknown command '" + command + "'");
        System.out.println("Unknown command '" + command + "'");
        System.out.println("For help type '" + help + "'");
    }


    private void initActions(IVideoPlayerCtrl ctrl) {
        actions = new TreeMap<>();
        actions.put("pl", new Play(ctrl));
        actions.put("ps", new Pause(ctrl));
        actions.put("+1", new PlusOneSec(ctrl));
        actions.put("-1", new MinusOneSec(ctrl));
        actions.put("start", new Start(ctrl));
        actions.put("end", new End(ctrl));
        actions.put("state", new State(ctrl));
        actions.put("time", new Time(ctrl));
        actions.put("ncf", new ActualFrame(ctrl));
        actions.put("vfr", new FrameRate(ctrl));
        actions.put("exit", new Exit(ctrl));
        actions.put("runTest", new RunTest(ctrl));
        actions.put("tmc", new TimeCode(ctrl));
        actions.put("slp", new Sleep(ctrl));
        actions.put("stopTest", new StopTest(ctrl));
        actions.put("buf", new BufferInfo(ctrl));
        logger.trace("Actions of command lind were inicialized");
    }

}
