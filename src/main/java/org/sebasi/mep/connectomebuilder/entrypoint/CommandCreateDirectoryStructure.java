package org.sebasi.mep.connectomebuilder.entrypoint;

import org.apache.commons.lang3.StringUtils;
import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenSpec;
import org.sebasi.mep.connectomebuilder.util.FileUtil;

import java.io.File;

public class CommandCreateDirectoryStructure extends CommandParent {

    public CommandCreateDirectoryStructure(Arguments arguments) {
        super(arguments);

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

        ConnectomeGenSpec cgs = ConnectomeGenSpec.fromJson(new File(cgsPathAndFilename));
        FileUtil.createDirectoryStructure(einDirectory, cgs);
    }
}
