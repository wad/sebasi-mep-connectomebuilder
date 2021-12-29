package org.sebasi.mep.connectomebuilder.component;

public class Synapse extends AbstractComponent {

    // A synapse belongs to the neuron on the post-synaptic side of the connection.
    // It does know the nid of the neuron on the pre-synaptic side, whose axon connects to this dendrite.
    private Nid preSynapticNid;

    private int synapseStrength;

    public Synapse(
            Nid preSynapticNid,
            int synapseStrength) {
        this.preSynapticNid = preSynapticNid;
        this.synapseStrength = synapseStrength;
    }

    public Nid getPreSynapticNid() {
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
