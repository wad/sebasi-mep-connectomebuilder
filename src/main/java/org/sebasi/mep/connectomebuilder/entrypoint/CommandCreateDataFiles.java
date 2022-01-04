package org.sebasi.mep.connectomebuilder.entrypoint;

import org.apache.commons.lang3.StringUtils;
import org.sebasi.mep.connectomebuilder.component.Body;
import org.sebasi.mep.connectomebuilder.component.Brain;
import org.sebasi.mep.connectomebuilder.component.Room;
import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenSpec;
import org.sebasi.mep.connectomebuilder.util.FileUtil;
import org.sebasi.mep.connectomebuilder.util.TextOutput;

import java.io.File;

public class CommandCreateDataFiles extends CommandParent {

    public CommandCreateDataFiles(Arguments arguments) {
        super(arguments);

        TextOutput.showMessage("Beginning to write data files.");

        String einParentDirectory = arguments.getEinParentDirectory();
        if (StringUtils.isBlank(einParentDirectory)) {
            throw new RuntimeException("Path to parent directory is required.");
        }

        String ein = arguments.getEin();
        if (StringUtils.isBlank(ein)) {
            throw new RuntimeException("EIN is required.");
        }
        String einDirectory = einParentDirectory + "/" + ein;

        // The file was previously copied to this location when the directory structure was created.
        ConnectomeGenSpec cgs = ConnectomeGenSpec.fromJson(
                new File(FileUtil.getCgsPathAndFilename(einDirectory)));

        writeRoom(einDirectory);
        writeBody(einDirectory);
        writeBrain(einDirectory, cgs);

        TextOutput.showMessage("Done writing data files.");
    }

    private void writeRoom(String einDirectory) {
        TextOutput.showMessage("Writing room.");
        Room room = new Room();
        room.writeInitialDataFiles(einDirectory);
        TextOutput.showMessage("Done writing room.");
    }

    private void writeBody(String einDirectory) {
        TextOutput.showMessage("Writing body.");
        Body body = new Body(arguments.getPersonName());
        body.writeInitialDataFiles(einDirectory);
        TextOutput.showMessage("Done writing body.");
    }

    private void writeBrain(
            String einDirectory,
            ConnectomeGenSpec cgs) {
        TextOutput.showMessage("Writing brain.");
        Brain brain = new Brain(cgs.getRegionSpecList().size());
        brain.writeInitialDataFiles(einDirectory, cgs);
        TextOutput.showMessage("Done writing brain.");
    }
}
