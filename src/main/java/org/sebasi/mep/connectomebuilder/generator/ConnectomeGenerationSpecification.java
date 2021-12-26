package org.sebasi.mep.connectomebuilder.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.sebasi.mep.connectomebuilder.generator.ConnectomeGenerationSpecificationVersion.DEFAULT_VERSION;

// A json representation of this class is used to configure a new connectome.
public class ConnectomeGenerationSpecification {

    ConnectomeGenerationSpecificationVersion connectomeGenerationSpecificationVersion;
    List<ClusterSpecification> clusterSpecificationList;

    private final ObjectMapper objectMapper;

    public ConnectomeGenerationSpecification() {
        this(DEFAULT_VERSION);
    }

    public ConnectomeGenerationSpecification(ConnectomeGenerationSpecificationVersion version) {
        this(version, new ObjectMapper());
    }

    public ConnectomeGenerationSpecification(
            ConnectomeGenerationSpecificationVersion version,
            ObjectMapper objectMapper) {
        this.connectomeGenerationSpecificationVersion = version;
        this.objectMapper = objectMapper;
    }

    public static ConnectomeGenerationSpecification fromJson(String json) {
        return fromJson(
                json,
                new ObjectMapper());
    }

    public static ConnectomeGenerationSpecification fromJson(
            String json,
            ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(json, ConnectomeGenerationSpecification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ConnectomeGenerationSpecificationVersion getConnectomeGenerationSpecificationVersion() {
        return connectomeGenerationSpecificationVersion;
    }

    public void setConnectomeGenerationSpecificationVersion(ConnectomeGenerationSpecificationVersion connectomeGenerationSpecificationVersion) {
        this.connectomeGenerationSpecificationVersion = connectomeGenerationSpecificationVersion;
    }

    public List<ClusterSpecification> getClusterSpecificationList() {
        return clusterSpecificationList;
    }

    public void setClusterSpecificationList(List<ClusterSpecification> clusterSpecificationList) {
        this.clusterSpecificationList = clusterSpecificationList;
    }
}
