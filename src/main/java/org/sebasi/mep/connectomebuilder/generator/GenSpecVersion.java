package org.sebasi.mep.connectomebuilder.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum GenSpecVersion {

    // Don't go back and change versions, unless you're okay with breaking backwards compatibility on previous json files.
    INITIAL_001("INITIAL_001");

    private final String label;

    public static final GenSpecVersion DEFAULT_VERSION = INITIAL_001;

    GenSpecVersion(String label) {
        this.label = label;
    }

    public static GenSpecVersion determineViaJson(
            String json,
            ObjectMapper objectMapper) {
        try {
            JsonNode jsonObject = objectMapper.readTree(json);
            JsonNode versionAsJsonObject = jsonObject.get("genSpecVersion");
            return determineViaVersionString(versionAsJsonObject.asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static GenSpecVersion determineViaVersionString(String valueFound) {
        GenSpecVersion[] values = values();
        for (GenSpecVersion value : values) {
            if (value.label.equals(valueFound)) {
                return value;
            }
        }
        throw new RuntimeException("Unsupported GenSpecVersion: '" + valueFound + "'");
    }
}
