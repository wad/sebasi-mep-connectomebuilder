package org.sebasi.mep.connectomebuilder;

import java.util.Arrays;
import java.util.List;

public class Demo {

    // todo: add JCommander
    public static void main(String... args) {
        Demo demo = new Demo();
        List<String> brainSpecs = Arrays.asList(args);
        demo.runDemo(brainSpecs);
    }

    private void runDemo(List<String> brainSpecs) {

        int numTicksToRun = 10;

        World world = new World();

        for (String brainSpec : brainSpecs) {
            world.generateBrain(brainSpec);
        }

        System.out.println("Starting world with " + brainSpecs.size() + " brains, running for " + numTicksToRun + " ticks.");
        world.startTicking(numTicksToRun);
        System.out.println("Ending world.");
    }
}
