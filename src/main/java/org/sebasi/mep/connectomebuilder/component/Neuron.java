package org.sebasi.mep.connectomebuilder.component;

import org.sebasi.mep.connectomebuilder.util.GlobalStaticHelper;
import org.sebasi.mep.connectomebuilder.util.NidUtil;

import java.util.ArrayList;
import java.util.List;

public class Neuron extends AbstractComponent {

    // A neuron has a bunch of synapses.
    private List<Synapse> dendriticSynapsesLocal;

    private short lid;
    private int accumulatedSignal = 0;
    private boolean justFired;
    private int firingThreshold = 10;

    public Neuron(
            short lid,
            int numInitialSynapsesLocalMin,
            int numInitialSynapsesLocalMax,
            int initialSynapticStrengthMin,
            int initialSynapticStrengthMax) {

        this.lid = lid;

        int numLocalSynapses = GlobalStaticHelper.getRandomUtil().getRandomNumber(
                numInitialSynapsesLocalMin,
                numInitialSynapsesLocalMax);
        if (numInitialSynapsesLocalMax > NidUtil.MAX_NUM_NEURONS_PER_CLUSTER) {
            throw new RuntimeException("Too many synapses requested: " + numInitialSynapsesLocalMax);
        }

        dendriticSynapsesLocal = new ArrayList<>(numLocalSynapses);

        // todo: Set up synapses that connect out of the cluster.
    }

    public void fireIfReady() {
        if (accumulatedSignal >= firingThreshold) {
            fire();
        }
    }

    void fire() {
        justFired = true;
        accumulatedSignal = 0;
    }

    public void checkUpstreamConnections(Brain neuronGroup) {
        for (Synapse synapse : dendriticSynapsesLocal) {
            if (didPreSynapticNeuronJustFire()) {
                accumulatedSignal += synapse.getSynapseStrength();
            }
        }
    }

    private boolean didPreSynapticNeuronJustFire() {
        return false; // todo
    }

    public boolean getDidJustFire() {
        return justFired;
    }

    public void resetFiringStatus() {
        justFired = false;
    }

    @Override
    public void report(StringBuilder builder) {
        builder.append("\nNumber of synapses: ").append(dendriticSynapsesLocal.size());
        for (Synapse dendriticSynapse : dendriticSynapsesLocal) {
            dendriticSynapse.report(builder);
        }
    }
}
