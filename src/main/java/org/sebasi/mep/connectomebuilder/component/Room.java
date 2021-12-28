package org.sebasi.mep.connectomebuilder.component;

import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenerationSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Room {

    List<Body> bodies = new ArrayList<>();

    // todo: World can include some other stuff in it later.

    public void generateBody(String pathToConnectomeSpecificationFile) {
        ConnectomeGenerationSpecification brainSpec = ConnectomeGenerationSpecification.fromJson(
                new File(pathToConnectomeSpecificationFile));
        bodies.add(new Body(new Brain(brainSpec)));
    }

    public void startTicking(int numTicksToRun) {
        for (Body body : bodies) {
            Brain brain = body.getBrain();
            for (int tick = 0; tick < numTicksToRun; tick++) {
                brain.processTick();
            }
        }
    }
}
