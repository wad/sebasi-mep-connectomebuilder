package org.sebasi.mep.connectomebuilder.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum ConnectomeGenerationSpecificationVersion {

    // Once we define a version, don't change it, as it will break backwards compatibility for saved json files.
    INITIAL_001("INITIAL_001");

    private final String label;

    public static final ConnectomeGenerationSpecificationVersion DEFAULT_VERSION = INITIAL_001;

    ConnectomeGenerationSpecificationVersion(String label) {
        this.label = label;
    }

    public static ConnectomeGenerationSpecificationVersion determineViaJson(
            String json,
            ObjectMapper objectMapper) {
        try {
            JsonNode jsonObject = objectMapper.readTree(json);
            JsonNode versionAsJsonObject = jsonObject.get("connectomeGenerationSpecificationVersion");
            return determineViaVersionString(versionAsJsonObject.asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConnectomeGenerationSpecificationVersion determineViaVersionString(String valueFound) {
        ConnectomeGenerationSpecificationVersion[] values = values();
        for (ConnectomeGenerationSpecificationVersion value : values) {
            if (value.label.equals(valueFound)) {
                return value;
            }
        }
        throw new RuntimeException("Unsupported ConnectomeGenerationSpecificationVersion: '" + valueFound + "'");
    }
}
