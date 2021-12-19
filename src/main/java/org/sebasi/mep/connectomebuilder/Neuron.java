package org.sebasi.mep.connectomebuilder;

import java.util.HashSet;
import java.util.Set;

public class Neuron {

    private Set<Synapse> dendriticSynapses = new HashSet<>();

    private int accumulatedSignal = 0;

    private boolean justFired;

    private int firingThreshold = 10;

    public void fireIfReady() {
        if (accumulatedSignal >= firingThreshold) {
            fire();
        }
    }

    void fire() {
        justFired = true;
        accumulatedSignal = 0;
    }

    public void checkUpstreamConnections(NeuronGroup neuronGroup) {
        for (Synapse synapse : dendriticSynapses) {
            if (neuronGroup.getNeuron(synapse.getAxonalNid()).getDidJustFire()) {
                accumulatedSignal += synapse.getSynapseStrength();
            }
        }
    }

    public boolean getDidJustFire() {
        return justFired;
    }

    public void resetFiringStatus() {
        justFired = false;
    }
}
