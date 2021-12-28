package org.sebasi.mep.connectomebuilder.component;

import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenSpec;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Room extends AbstractComponent {

    // A room can hold zero or more bodies.
    List<Body> bodies = new ArrayList<>();

    // todo: World can include some other environmental stuff in it later.
    // bodies can move around in the room. Different parts of the room can have
    // different impacts on the wellness.

    public void generateBody(String pathToConnectomeSpecificationFile) {
        ConnectomeGenSpec brainSpec = ConnectomeGenSpec.fromJson(
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
