package org.sebasi.mep.connectomebuilder.component;

import org.sebasi.mep.connectomebuilder.generator.ClusterSpec;
import org.sebasi.mep.connectomebuilder.util.GlobalStaticHelper;
import org.sebasi.mep.connectomebuilder.util.NidUtil;

import java.util.ArrayList;
import java.util.List;

public class Cluster extends AbstractComponent {

    short cid;
    List<Neuron> neurons;

    public Cluster(
            short cid,
            ClusterSpec clusterSpec) {

        int numNeuronsMin = clusterSpec.getNumNeuronsMin();
        int numNeuronsMax = clusterSpec.getNumNeuronsMax();
        if (numNeuronsMin < 0 || numNeuronsMin > numNeuronsMax) {
            throw new RuntimeException("Invalid min number of neurons: " + numNeuronsMin);
        }
        if (numNeuronsMax > NidUtil.MAX_NUM_NEURONS_PER_CLUSTER) {
            throw new RuntimeException("Invalid max number of neurons: " + numNeuronsMax);
        }

        int numNeurons = GlobalStaticHelper.getRandomUtil().getRandomNumber(
                numNeuronsMin,
                numNeuronsMax);
        neurons = new ArrayList<>(numNeurons);
        for (short lid = 0; lid < numNeurons; lid++) {
            Neuron neuron = new Neuron(
                    lid,
                    clusterSpec.getNumSynapsesLocalMin(),
                    clusterSpec.getNumSynapsesLocalMax(),
                    clusterSpec.getInitialSynapticStrengthMin(),
                    clusterSpec.getInitialSynapticStrengthMax());
            neurons.add(neuron);
        }

        this.cid = cid;
    }

    @Override
    public void report(StringBuilder builder) {
        builder.append("\nNumber of neurons: ").append(neurons.size());
    }
}
