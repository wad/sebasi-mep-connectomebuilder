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

    public CommandRunRegion(Arguments arguments) {
        super(arguments);

        controlDirectory = arguments.getControlDirectory();
        rid = arguments.getRid();
        ridAsHexString = NidUtil.convertRidToHexString(rid);

        run();
    }

    void run() {

        TextOutput.showMessage("Starting region. Rid=" + ridAsHexString);

        while (true) {

            if (isShutdownDetected()) {
                TextOutput.showMessage("Shutdown detected. Exiting region cleanly. Rid=" + ridAsHexString);
                FileUtil.deleteShutdownFile(controlDirectory, rid);
                break;
            }

            if (isReadyToTick()) {
                processTick();
            } else {
                Delayer.delay(1000);
            }
        }
    }

    boolean isShutdownDetected() {
        return FileUtil.isShutdownFilePresent(controlDirectory, rid);
    }

    boolean isReadyToTick() {
        // If the driving computer deleted the ready file, it means I'm ready to run a tick.
        return !FileUtil.isReadyFilePresent(controlDirectory, rid);
    }

    void processTick() {
        numTicksThisSession++;
    }
}
