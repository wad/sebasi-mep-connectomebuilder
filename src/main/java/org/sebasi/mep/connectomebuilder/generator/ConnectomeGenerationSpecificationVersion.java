package org.sebasi.mep.connectomebuilder.generator;

public enum ConnectomeGenerationSpecificationVersion {

    // Once we define a version, don't change it, as it will break backwards compatibility for saved json files.
    INITIAL_001("INITIAL_001");

    private final String label;

    ConnectomeGenerationSpecificationVersion(String label) {
        this.label = label;
    }

    public static ConnectomeGenerationSpecificationVersion determine(String valueFound) {
        ConnectomeGenerationSpecificationVersion[] values = values();
        for (ConnectomeGenerationSpecificationVersion value : values) {
            if (value.label.equals(valueFound)) {
                return value;
            }
        }
        throw new RuntimeException("Unsupported ConnectomeGenerationSpecificationVersion: '" + valueFound + "'");
    }
}
