package org.sebasi.mep.connectomebuilder.entrypoint;

import org.sebasi.mep.connectomebuilder.component.Room;
import org.sebasi.mep.connectomebuilder.util.SaveFile;

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
        room.performTicks(numTicksToRun);
        System.out.println("Ticking completed.");

        StringBuilder builder = new StringBuilder("Report:");
        room.report(builder);
        System.out.println(builder);

        String saveFileWithPath = arguments.getEinParentDirectory();
        System.out.println("Saving to " + saveFileWithPath);
        SaveFile saveFile = new SaveFile(saveFileWithPath);
        saveFile.prepareForSaving();
        //room.save(saveFile);
        System.out.println("Saved.");
    }
}
