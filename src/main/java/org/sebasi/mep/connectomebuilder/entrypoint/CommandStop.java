package org.sebasi.mep.connectomebuilder.entrypoint;

import org.sebasi.mep.connectomebuilder.util.FileUtil;

public class CommandStop extends CommandParent {

    public CommandStop(
            Arguments arguments,
            boolean stopAllRemotesAlso) {
        super(arguments);

        String ein = arguments.getEin();
        String einDirectory = arguments.getEinParentDirectory() + "/" + ein;

        FileUtil.createStopDriverFile(einDirectory);
        if (stopAllRemotesAlso) {
            FileUtil.createStopAllRemotesFile(einDirectory);
        }
    }
}
