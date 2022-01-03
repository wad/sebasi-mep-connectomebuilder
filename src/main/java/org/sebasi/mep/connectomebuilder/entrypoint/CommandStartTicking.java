package org.sebasi.mep.connectomebuilder.entrypoint;

import java.io.File;

public class CommandStartTicking extends CommandParent {

    // ein is the Emulation Instance Name, basically the name of the subdirectory that contains all the files.
    String ein;
    String einDirectoryPath;
    File workingDirectory;

    public CommandStartTicking(Arguments arguments) {
        super(arguments);

        ein = arguments.getEin();
        einDirectoryPath = arguments.getEinParentDirectory() + "/" + ein;
        workingDirectory = new File(einDirectoryPath);

        verifyFileStructureSeemsGood();

        run();
    }

    void verifyFileStructureSeemsGood() {
        if (!workingDirectory.exists()) {
            throw new RuntimeException("Working directory doesn't exist: " + einDirectoryPath);
        }
    }

    void run() {
        // todo
    }
}
