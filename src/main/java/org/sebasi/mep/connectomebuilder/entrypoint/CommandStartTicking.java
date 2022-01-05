package org.sebasi.mep.connectomebuilder.entrypoint;

import org.sebasi.mep.connectomebuilder.util.FileUtil;
import org.sebasi.mep.connectomebuilder.util.TextOutput;

import java.io.File;

public class CommandStartTicking extends CommandParent {

    String ein;
    String einDirectory;
    long numTicksThisSession = 0L;

    public CommandStartTicking(Arguments arguments) {
        super(arguments);

        ein = arguments.getEin();
        einDirectory = arguments.getEinParentDirectory() + "/" + ein;

        verifyFileStructureSeemsGood();
        FileUtil.deleteStopFiles(einDirectory);

        run();
    }

    void verifyFileStructureSeemsGood() {
        File workingDirectory = new File(einDirectory);
        if (!workingDirectory.exists()) {
            throw new RuntimeException("Working directory doesn't exist: " + einDirectory);
        }
    }

    void run() {
        while (true) {
            if (FileUtil.isStopDriverFilePresent(einDirectory)) {
                TextOutput.showMessage("Exiting driver cleanly after " + numTicksThisSession + " ticks.");
                break;
            }

            processTick();
        }
    }

    void processTick() {
        numTicksThisSession++;
    }
}
