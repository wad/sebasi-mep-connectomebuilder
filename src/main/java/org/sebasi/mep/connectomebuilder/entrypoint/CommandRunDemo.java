package org.sebasi.mep.connectomebuilder.entrypoint;

import org.sebasi.mep.connectomebuilder.component.Room;

import java.util.List;

public class CommandRunDemo extends CommandParent {

    public CommandRunDemo(Arguments arguments) {
        super(arguments);
        run();
    }

    public void run() {
        int numTicksToRun = arguments.getNumTicks();

        Room room = new Room();

        List<String> brainSpecs = arguments.getBrainSpecs();
        for (String brainSpec : brainSpecs) {
            room.generateBody(brainSpec);
        }

        System.out.println("Starting room with " + brainSpecs.size() + " bodies, running for " + numTicksToRun + " ticks.");
        room.startTicking(numTicksToRun);
        System.out.println("Ending room.");
    }
}
