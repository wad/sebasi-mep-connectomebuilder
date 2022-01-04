package org.sebasi.mep.connectomebuilder.entrypoint;

import com.beust.jcommander.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.sebasi.mep.connectomebuilder.util.NidUtil;

public class Arguments {

    @Parameter(
            names = {"--help"},
            description = "Shows this help message."
    )
    boolean help = false;

    @Parameter(
            names = {"--command"},
            description = "Required. Here you specify what you want to do."
    )
    Command command;

    @Parameter(
            names = {"--einParentDirectory"},
            description = "The full absolute path to where the EIN is, or to where it should be created."
    )
    String einParentDirectory;

    @Parameter(
            names = {"--ein"},
            description = "The emulation instance name, which is the same as the subdirectory name where all the files are stored. Don't use spaces."
    )
    String ein;

    @Parameter(
            names = {"--controlDirectory"},
            description = "The full path to the control directory, without a slash on the end. Used when running a region."
    )
    String controlDirectory;

    @Parameter(
            names = {"--cgs"},
            description = "Path and filename of the JSON-format Connectome Generation Specification file."
    )
    String cgsPathAndFilename;

    @Parameter(
            names = {"--rid"},
            description = "The region identifier of the region to run, all caps, hexadecimal form."
    )
    String rid;

    public boolean isHelp() {
        return help;
    }

    public Command getCommand() {
        return command;
    }

    public String getEinParentDirectory() {
        return einParentDirectory;
    }

    public String getEin() {
        if (StringUtils.isBlank(ein)) {
            throw new RuntimeException("The EIN is required.");
        }
        if (ein.indexOf(' ') != -1) {
            throw new RuntimeException("Don't use spaces in the EIN.");
        }
        return ein;
    }

    public byte getRid() {
        if (StringUtils.isBlank(rid)) {
            throw new RuntimeException("The RID is required.");
        }
        return NidUtil.getRidFromHexString(rid);
    }

    public String getControlDirectory() {
        return controlDirectory;
    }

    public String getCgsPathAndFilename() {
        return cgsPathAndFilename;
    }
}
