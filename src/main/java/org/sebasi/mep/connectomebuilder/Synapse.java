package org.sebasi.mep.connectomebuilder;

public class Synapse {

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
