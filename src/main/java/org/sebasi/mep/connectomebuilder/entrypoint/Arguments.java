package org.sebasi.mep.connectomebuilder.entrypoint;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            names = {"--brainSpecs"},
            description = "Comma-delimited list of path-and-filenames to json files specifying brain setups."
    )
    String brainSpecs;

    @Parameter(
            names = {"--numTicks"},
            description = "Number of ticks to run before exiting."
    )
    int numTicks;

    public boolean isHelp() {
        return help;
    }

    public Command getCommand() {
        return command;
    }

    public List<String> getBrainSpecs() {
        if (brainSpecs == null) {
            return new ArrayList<>();
        }

        return Arrays.asList(brainSpecs.split(","));
    }

    public int getNumTicks() {
        return numTicks;
    }
}
