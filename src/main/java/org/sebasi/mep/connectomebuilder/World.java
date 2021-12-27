package org.sebasi.mep.connectomebuilder;

import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenerationSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class World {

    List<Brain> brains = new ArrayList<>();

    // todo: World can include some other stuff in it later.

    public void generateBrain(String pathToConnectomeSpecificationFile) {
        ConnectomeGenerationSpecification brainSpec = ConnectomeGenerationSpecification.fromJson(
                new File(pathToConnectomeSpecificationFile));
        brains.add(new Brain(brainSpec));
    }

    public void startTicking(int numTicksToRun) {
        for (Brain brain : brains) {
            for (int tick = 0; tick < numTicksToRun; tick++) {
                brain.processTick();
            }
        }
    }
}
