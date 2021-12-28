package org.sebasi.mep.connectomebuilder.component;

public class Synapse extends AbstractComponent {

    // A synapse belongs to the neuron on the post-synaptic side of the connection.
    // It does know the nid of the neuron on the pre-synaptic side, whose axon connects to this dendrite.
    private Nid axonalNid;

    private int synapseStrength;

    public Synapse(
            Nid axonalNid,
            int synapseStrength) {
        this.axonalNid = axonalNid;
        this.synapseStrength = synapseStrength;
    }

    public Nid getAxonalNid() {
        return axonalNid;
    }

    public int getSynapseStrength() {
        return synapseStrength;
    }
}
