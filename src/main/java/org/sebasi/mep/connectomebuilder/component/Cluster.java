package org.sebasi.mep.connectomebuilder.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sebasi.mep.connectomebuilder.generator.ClusterSpec;
import org.sebasi.mep.connectomebuilder.util.FileUtil;
import org.sebasi.mep.connectomebuilder.util.GlobalStaticHelper;
import org.sebasi.mep.connectomebuilder.util.NidUtil;

import java.util.ArrayList;
import java.util.List;

public class Cluster extends AbstractComponent {

    byte rid;
    short cid;
    List<Neuron> neurons = null;

    public Cluster(
            byte rid,
            short cid,
            String einDirectory) {
        this(rid, cid);
        loadNeuronsFromDisk(einDirectory);
    }

    public Cluster(
            byte rid,
            short cid) {
        this.rid = rid;
        this.cid = cid;
    }

    public void loadNeuronsFromDisk(String einDirectory) {
        // todo: use the rid and cid to get the path to the cluster directory, and load the neurons into RAM.
    }

    void generateInitialNeurons(ClusterSpec clusterSpec) {
        int numNeuronsMin = clusterSpec.getNumNeuronsMin();
        int numNeuronsMax = clusterSpec.getNumNeuronsMax();
        if (numNeuronsMin < 0 || numNeuronsMin > numNeuronsMax) {
            throw new RuntimeException("Invalid min number of neurons: " + numNeuronsMin);
        }
        if (numNeuronsMax > NidUtil.MAX_NUM_NEURONS_PER_CLUSTER) {
            throw new RuntimeException("Invalid max number of neurons: " + numNeuronsMax);
        }

        int numNeurons = GlobalStaticHelper.randomUtil.getRandomNumber(
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
    }

    public void writeInitialDataFiles(
            String einDirectory,
            ClusterSpec clusterSpec) {
        generateInitialNeurons(clusterSpec);

        try {
            FileUtil.writeCluster(
                    einDirectory,
                    GlobalStaticHelper.objectMapper.writeValueAsString(this),
                    rid,
                    cid);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problem serializing cluster into json: " + e.getMessage(), e);
        }
    }

    @Override
    public void report(StringBuilder builder) {
        builder.append("\nNumber of neurons: ").append(neurons.size());
    }
}
