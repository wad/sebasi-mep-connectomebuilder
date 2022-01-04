package org.sebasi.mep.connectomebuilder.entrypoint;

import org.apache.commons.lang3.StringUtils;
import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenSpec;
import org.sebasi.mep.connectomebuilder.util.FileUtil;
import org.sebasi.mep.connectomebuilder.util.TextOutput;

public class CommandCreateDirectoryStructure extends CommandParent {

    public CommandCreateDirectoryStructure(Arguments arguments) {
        super(arguments);

        TextOutput.showMessage("Starting to create directory structure.");

        String einParentDirectory = arguments.getEinParentDirectory();
        if (StringUtils.isBlank(einParentDirectory)) {
            throw new RuntimeException("Path to parent directory is required.");
        }

        String ein = arguments.getEin();
        if (StringUtils.isBlank(ein)) {
            throw new RuntimeException("EIN is required.");
        }
        String einDirectory = einParentDirectory + "/" + ein;

        String cgsPathAndFilename = arguments.getCgsPathAndFilename();
        if (StringUtils.isBlank(cgsPathAndFilename)) {
            throw new RuntimeException("CGS is required.");
        }

        String cgsJson = FileUtil.readFileIntoString(cgsPathAndFilename);
        ConnectomeGenSpec cgs = ConnectomeGenSpec.fromJson(cgsJson);
        FileUtil.createDirectoryStructure(einDirectory, cgs);

        // We also keep a copy of the CGS file in our directory structure, for future reference.
        FileUtil.writeCgsFile(einDirectory, cgsJson);

        TextOutput.showMessage("Done creating directory structure." +
                " You can now move regions onto their own machines, and mount them with NFS." +
                " Be sure to also create a mount to the control directory, from each region machine.");
    }
}
