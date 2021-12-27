package org.sebasi.mep.connectomebuilder;

import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenerationSpecification;

import java.util.HashMap;
import java.util.Map;

public class Brain {

    private Map<Nid, Neuron> neuronsByNid = new HashMap<>();

    public Brain(ConnectomeGenerationSpecification spec) {
        // todo: Apply the spec to generate a brain.
    }

    public Neuron getNeuron(Nid nid) {
        return neuronsByNid.get(nid);
    }

    public void processTick() {
        neuronsByNid.values().forEach(Neuron::fireIfReady);
        neuronsByNid.values().forEach(n -> n.checkUpstreamConnections(this));
        neuronsByNid.values().forEach(Neuron::resetFiringStatus);
    }
}
