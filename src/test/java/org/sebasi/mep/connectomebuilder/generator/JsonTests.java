package org.sebasi.mep.connectomebuilder.generator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTests {

    @Test
    public void testRoundTrips() {
        ConnectomeGenerationSpecification specOriginal = makeSpec();
        String jsonStringOriginal = specOriginal.toJson();
        assertEquals(
                "{\"connectomeGenerationSpecificationVersion\":\"INITIAL_001\",\"clusterSpecificationList\":[{\"numNeuronsMin\":10,\"numNeuronsMax\":20,\"eachNeuronListensToHowManyOtherNeuronsInThisCluster\":3},{\"numNeuronsMin\":100,\"numNeuronsMax\":200,\"eachNeuronListensToHowManyOtherNeuronsInThisCluster\":30}]}",
                jsonStringOriginal);
        ConnectomeGenerationSpecification fromString = ConnectomeGenerationSpecification.fromJson(jsonStringOriginal);
        assertEquals(jsonStringOriginal, fromString.toJson());
    }

    ConnectomeGenerationSpecification makeSpec() {
        ClusterSpecification clusterSpec1 = new ClusterSpecification();
        clusterSpec1.numNeuronsMin = 10;
        clusterSpec1.numNeuronsMax = 20;
        clusterSpec1.eachNeuronListensToHowManyOtherNeuronsInThisCluster = 3;

        ClusterSpecification clusterSpec2 = new ClusterSpecification();
        clusterSpec2.numNeuronsMin = 100;
        clusterSpec2.numNeuronsMax = 200;
        clusterSpec2.eachNeuronListensToHowManyOtherNeuronsInThisCluster = 30;

        ConnectomeGenerationSpecification spec = new ConnectomeGenerationSpecification();
        List<ClusterSpecification> clusterSpecs = new ArrayList<>();
        clusterSpecs.add(clusterSpec1);
        clusterSpecs.add(clusterSpec2);
        spec.clusterSpecificationList = clusterSpecs;

        return spec;
    }
}
