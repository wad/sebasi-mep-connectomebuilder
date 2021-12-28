package org.sebasi.mep.connectomebuilder.entrypoint;

import org.sebasi.mep.connectomebuilder.World;

import java.util.List;

public class CommandRunDemo extends CommandParent {

    public CommandRunDemo(Arguments arguments) {
        super(arguments);
        run();
    }

    public void run() {
        int numTicksToRun = 10;

        World world = new World();

        List<String> brainSpecs = arguments.getBrainSpecs();
        for (String brainSpec : brainSpecs) {
            world.generateBrain(brainSpec);
        }

        System.out.println("Starting world with " + brainSpecs.size() + " brains, running for " + numTicksToRun + " ticks.");
        world.startTicking(numTicksToRun);
        System.out.println("Ending world.");
    }
}
