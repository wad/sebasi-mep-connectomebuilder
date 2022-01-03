package org.sebasi.mep.connectomebuilder.component;

import org.sebasi.mep.connectomebuilder.util.NidUtil;

public class Synapse extends AbstractComponent {

    // A synapse belongs to the neuron on the post-synaptic side of the connection.
    // It does know the nid of the neuron on the pre-synaptic side, whose axon connects to this dendrite.
    private NidUtil preSynapticNid;

    private int synapseStrength;

    public Synapse(
            NidUtil preSynapticNid,
            int synapseStrength) {
        this.preSynapticNid = preSynapticNid;
        this.synapseStrength = synapseStrength;
    }

    public NidUtil getPreSynapticNid() {
        return preSynapticNid;
    }

    public int getSynapseStrength() {
        return synapseStrength;
    }

    @Override
    public void report(StringBuilder builder) {
        builder.append("\nPre-synaptic nid: ").append(preSynapticNid.toString());
        builder.append("\nStrength: ").append(synapseStrength);
    }
}
