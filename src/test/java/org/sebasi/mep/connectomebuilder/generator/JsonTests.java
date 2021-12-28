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
                "{\"genSpecVersion\":\"INITIAL_001\",\"clusterSpecList\":[" +
                        "{\"numNeuronsMin\":10,\"numNeuronsMax\":20,\"eachNeuronListensToHowManyOtherNeuronsInThisCluster\":3}" +
                        ",{\"numNeuronsMin\":100,\"numNeuronsMax\":200,\"eachNeuronListensToHowManyOtherNeuronsInThisCluster\":30}]}",
                jsonStringOriginal);
        ConnectomeGenSpec fromString = ConnectomeGenSpec.fromJson(jsonStringOriginal);
        assertEquals(jsonStringOriginal, fromString.toJson());
    }

    ConnectomeGenSpec makeSpec() {
        ClusterSpec clusterSpec1 = new ClusterSpec();
        clusterSpec1.numNeuronsMin = 10;
        clusterSpec1.numNeuronsMax = 20;
        clusterSpec1.eachNeuronListensToHowManyOtherNeuronsInThisCluster = 3;

        ClusterSpec clusterSpec2 = new ClusterSpec();
        clusterSpec2.numNeuronsMin = 100;
        clusterSpec2.numNeuronsMax = 200;
        clusterSpec2.eachNeuronListensToHowManyOtherNeuronsInThisCluster = 30;

        ConnectomeGenSpec spec = new ConnectomeGenSpec();
        List<ClusterSpec> clusterSpecs = new ArrayList<>();
        clusterSpecs.add(clusterSpec1);
        clusterSpecs.add(clusterSpec2);
        spec.clusterSpecList = clusterSpecs;

        return spec;
    }
}
