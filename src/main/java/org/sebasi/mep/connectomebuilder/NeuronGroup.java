package org.sebasi.mep.connectomebuilder;

import java.util.HashMap;
import java.util.Map;

public class NeuronGroup {

    private Map<Nid, Neuron> neuronsByNid = new HashMap<>();

    public Neuron getNeuron(Nid nid) {
        return neuronsByNid.get(nid);
    }

    public void processTick() {
        neuronsByNid.values().forEach(Neuron::fireIfReady);
        neuronsByNid.values().forEach(n -> n.checkUpstreamConnections(this));
        neuronsByNid.values().forEach(Neuron::resetFiringStatus);
    }
}
