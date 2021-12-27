package org.sebasi.mep.connectomebuilder.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.sebasi.mep.connectomebuilder.generator.ConnectomeGenerationSpecificationVersion.DEFAULT_VERSION;

// A json representation of this class is used to configure a new connectome.
public class ConnectomeGenerationSpecification {

    ConnectomeGenerationSpecificationVersion connectomeGenerationSpecificationVersion;

    List<ClusterSpecification> clusterSpecificationList;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ConnectomeGenerationSpecification() {
        this.connectomeGenerationSpecificationVersion = DEFAULT_VERSION;
    }

    public static ConnectomeGenerationSpecification fromJson(File fileWithJson) {
        try {
            return objectMapper.readValue(fileWithJson, ConnectomeGenerationSpecification.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + fileWithJson.getAbsolutePath(), e);
        }
    }

    public static ConnectomeGenerationSpecification fromJson(String json) {
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
