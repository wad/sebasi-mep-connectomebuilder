package org.sebasi.mep.connectomebuilder.generator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTests {

    @Test
    public void testRoundTrips() {
        ConnectomeGenSpec specOriginal = makeSpec();
        String jsonStringOriginal = specOriginal.toJson();
        assertEquals(
                "{\"genSpecVersion\":\"INITIAL_001\",\"regionSpecList\":[{\"clusterSpecList\":[{\"numNeuronsMin\":10,\"numNeuronsMax\":20,\"numSynapsesLocalMin\":4,\"numSynapsesLocalMax\":6,\"initialSynapticStrengthMin\":1,\"initialSynapticStrengthMax\":10},{\"numNeuronsMin\":100,\"numNeuronsMax\":200,\"numSynapsesLocalMin\":40,\"numSynapsesLocalMax\":60,\"initialSynapticStrengthMin\":10,\"initialSynapticStrengthMax\":100}]}]}",
                jsonStringOriginal);
        ConnectomeGenSpec fromString = ConnectomeGenSpec.fromJson(jsonStringOriginal);
        assertEquals(jsonStringOriginal, fromString.toJson());
    }

    ConnectomeGenSpec makeSpec() {
        ClusterSpec clusterSpec1 = new ClusterSpec();
        clusterSpec1.numNeuronsMin = 10;
        clusterSpec1.numNeuronsMax = 20;
        clusterSpec1.numSynapsesLocalMin = 4;
        clusterSpec1.numSynapsesLocalMax = 6;
        clusterSpec1.initialSynapticStrengthMin = 1;
        clusterSpec1.initialSynapticStrengthMax = 10;

        ClusterSpec clusterSpec2 = new ClusterSpec();
        clusterSpec2.numNeuronsMin = 100;
        clusterSpec2.numNeuronsMax = 200;
        clusterSpec2.numSynapsesLocalMin = 40;
        clusterSpec2.numSynapsesLocalMax = 60;
        clusterSpec2.initialSynapticStrengthMin = 10;
        clusterSpec2.initialSynapticStrengthMax = 100;

        List<ClusterSpec> clusterSpecList = new ArrayList<>();
        clusterSpecList.add(clusterSpec1);
        clusterSpecList.add(clusterSpec2);

        RegionSpec regionSpec = new RegionSpec();
        regionSpec.setClusterSpecList(clusterSpecList);

        List<RegionSpec> regionSpecList = new ArrayList<>();
        regionSpecList.add(regionSpec);

        ConnectomeGenSpec spec = new ConnectomeGenSpec();
        spec.setRegionSpecList(regionSpecList);

        return spec;
    }
}
