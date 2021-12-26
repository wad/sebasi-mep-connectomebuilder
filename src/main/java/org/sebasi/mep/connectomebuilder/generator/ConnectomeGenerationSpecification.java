package org.sebasi.mep.connectomebuilder.generator;

import java.util.List;

// A json representation of this class is used to configure a new connectome.
public class ConnectomeGenerationSpecification {

    ConnectomeGenerationSpecificationVersion connectomeGenerationSpecificationVersion;
    List<ClusterSpecification> clusterSpecificationList;

    public ConnectomeGenerationSpecification(String json) {
        // todo
    }

    public String toJson(ConnectomeGenerationSpecification connectomeGenerationSpecification) {
        // todo
        return "";
    }
}
