package org.sebasi.mep.connectomebuilder.entrypoint;

import org.sebasi.mep.connectomebuilder.util.NidUtil;
import org.sebasi.mep.connectomebuilder.util.Delayer;
import org.sebasi.mep.connectomebuilder.util.FileUtil;
import org.sebasi.mep.connectomebuilder.util.TextOutput;

public class CommandRunRegion extends CommandParent {

    String controlDirectory;
    byte rid;
    String ridAsHexString;
    long numTicksThisSession = 0L;
    int tickPollingSleepInMilliseconds;

    public CommandRunRegion(Arguments arguments) {
        super(arguments);

        tickPollingSleepInMilliseconds = arguments.getTickPollingSleep();
        controlDirectory = arguments.getControlDirectory();
        rid = arguments.getRid();
        ridAsHexString = NidUtil.convertRidToHexString(rid);

        run();
    }

    void run() {

        TextOutput.showMessage("Starting region. Rid=" + ridAsHexString);

        while (true) {

            if (isStopAllRemotesDetected()) {
                TextOutput.showMessage("Stop all remotes file detected. Exiting region cleanly after " + numTicksThisSession + " ticks. Rid=" + ridAsHexString);
                break;
            }

            if (isReadyToTick()) {
                processTick();
            } else {
                Delayer.delay(tickPollingSleepInMilliseconds);
            }
        }
    }

    boolean isStopAllRemotesDetected() {
        return FileUtil.isStopAllRemotesFilePresent(controlDirectory);
    }

    boolean isReadyToTick() {
        // If the driving computer deleted the ready file, it means I'm ready to run a tick.
        return !FileUtil.isReadyFilePresent(controlDirectory, rid);
    }

    void processTick() {
        numTicksThisSession++;
    }
}
