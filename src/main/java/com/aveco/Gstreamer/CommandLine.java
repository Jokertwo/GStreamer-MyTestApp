package com.aveco.Gstreamer;

import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aveco.Gstreamer.action.ActualFrame;
import com.aveco.Gstreamer.action.BufferInfo;
import com.aveco.Gstreamer.action.CtrlAction;
import com.aveco.Gstreamer.action.TestAction;
import com.aveco.Gstreamer.action.End;
import com.aveco.Gstreamer.action.Exit;
import com.aveco.Gstreamer.action.FrameRate;
import com.aveco.Gstreamer.action.RewindBack;
import com.aveco.Gstreamer.action.Pause;
import com.aveco.Gstreamer.action.Play;
import com.aveco.Gstreamer.action.PlayFrameBack;
import com.aveco.Gstreamer.action.PlayFrameFront;
import com.aveco.Gstreamer.action.RewindFront;
import com.aveco.Gstreamer.action.RunTest;
import com.aveco.Gstreamer.action.Seek;
import com.aveco.Gstreamer.action.Sleep;
import com.aveco.Gstreamer.action.Start;
import com.aveco.Gstreamer.action.State;
import com.aveco.Gstreamer.action.StepBack;
import com.aveco.Gstreamer.action.StepForward;
import com.aveco.Gstreamer.action.StopTest;
import com.aveco.Gstreamer.action.Time;
import com.aveco.Gstreamer.action.TimeCode;
import com.aveco.Gstreamer.ctrl.IVideoPlayerCtrl;


public class CommandLine implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLine.class);

    private final String help = "man";
    private Map<String, CtrlAction> actions;
    private CommandBuffer buffer;


    public CommandLine(IVideoPlayerCtrl ctrl, CommandBuffer buffer) {
        super();
        this.buffer = buffer;
        initActions(ctrl);
    }


    @Override
    public void run() {
        logger.info("Thread for command line begin run");
        Thread.currentThread().setName("Command-console");
        String commands;
        while (true) {
            commands = buffer.getCommand();

            if (commands.trim().isEmpty()) {
                continue;
            }
            // print help
            if (commands.equals(help)) {
                printHelp();
                continue;
            }
            String[] splitCommand = commands.split(" ");
            // check command
            if (actions.containsKey(splitCommand[0])) {
                try {
                    actions.get(splitCommand[0]).doIt(splitCommand);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Exception in commandLine");
                }
            } else {
                unknownCoomand(commands);
            }
        }
    }


    private void printHelp() {
        for (Map.Entry<String, CtrlAction> entry : actions.entrySet()) {
            logger.info(entry.getKey() + "\t" + entry.getValue().help());
        }
    }


    private void unknownCoomand(String command) {
        logger.warn("Unknown command '" + command + "'");
        logger.info("For help type '" + help + "'");
    }


    private void initActions(IVideoPlayerCtrl ctrl) {
        actions = new TreeMap<>();
        actions.put("pl", new Play(ctrl));
        actions.put("ps", new Pause(ctrl));
        actions.put("+1", new RewindFront(ctrl));
        actions.put("-1", new RewindBack(ctrl));
        actions.put("start", new Start(ctrl));
        actions.put("end", new End(ctrl));
        actions.put("state", new State(ctrl));
        actions.put("time", new Time(ctrl));
        actions.put("ncf", new ActualFrame(ctrl));
        actions.put("vfr", new FrameRate(ctrl));
        actions.put("exit", new Exit(ctrl));
        actions.put("runT", new RunTest(ctrl));
        actions.put("tmc", new TimeCode(ctrl));
        actions.put("slp", new Sleep(ctrl));
        actions.put("stopT", new StopTest(ctrl));
        actions.put("buf", new BufferInfo(ctrl));
        actions.put("stepf", new StepForward(ctrl));
        actions.put("stepb", new StepBack(ctrl));
        actions.put("cp", new TestAction(ctrl));
        actions.put("plff", new PlayFrameFront(ctrl));
        actions.put("plfb", new PlayFrameBack(ctrl));
        actions.put("seek", new Seek(ctrl));
        logger.trace("Actions of command lind were inicialized");
    }

}
