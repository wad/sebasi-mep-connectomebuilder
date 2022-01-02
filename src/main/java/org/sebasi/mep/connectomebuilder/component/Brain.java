package org.sebasi.mep.connectomebuilder.component;

import org.sebasi.mep.connectomebuilder.generator.ClusterSpec;
import org.sebasi.mep.connectomebuilder.generator.ConnectomeGenSpec;
import org.sebasi.mep.connectomebuilder.util.GlobalStaticHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Brain extends AbstractComponent {

    // A brain has a bunch of neurons.
    private Map<NidUtil, Neuron> neuronsByNid = new HashMap<>();

    public Brain(ConnectomeGenSpec spec) {
        for (ClusterSpec clusterSpec : spec.getClusterSpecList()) {
            int numNeurons = GlobalStaticHelper.getRandomUtil().getRandomNumber(
                    clusterSpec.getNumNeuronsMin(),
                    clusterSpec.getNumNeuronsMax());
            List<NidUtil> cluster = new ArrayList<>(numNeurons);
            for (int i = 0; i < numNeurons; i++) {
                Neuron neuron = new Neuron();
//                NidUtil nid = new NidUtil();
//                cluster.add(nid);
//                neuronsByNid.put(nid, neuron);
            }
        }
    }

    public Neuron getNeuron(NidUtil nid) {
        return neuronsByNid.get(nid);
    }

    public void processTick() {
        neuronsByNid.values().forEach(Neuron::fireIfReady);
        neuronsByNid.values().forEach(n -> n.checkUpstreamConnections(this));
        neuronsByNid.values().forEach(Neuron::resetFiringStatus);
    }

    @Override
    public void report(StringBuilder builder) {
        builder.append("\nNumber of neurons: ").append(neuronsByNid.size());
    }
}
