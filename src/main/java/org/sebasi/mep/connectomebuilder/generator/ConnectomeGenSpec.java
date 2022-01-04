package org.sebasi.mep.connectomebuilder.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sebasi.mep.connectomebuilder.util.GlobalStaticHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.sebasi.mep.connectomebuilder.generator.GenSpecVersion.DEFAULT_VERSION;

// A json representation of this class is used to configure a new connectome.
public class ConnectomeGenSpec {

    GenSpecVersion genSpecVersion;
    List<RegionSpec> regionSpecList;

    public ConnectomeGenSpec() {
        this.genSpecVersion = DEFAULT_VERSION;
    }

    public static ConnectomeGenSpec fromJson(File fileWithJson) {
        try {
            return GlobalStaticHelper.objectMapper.readValue(fileWithJson, ConnectomeGenSpec.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + fileWithJson.getAbsolutePath(), e);
        }
    }

    public static ConnectomeGenSpec fromJson(String json) {
        try {
            return GlobalStaticHelper.objectMapper.readValue(json, ConnectomeGenSpec.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String toJson() {
        try {
            return GlobalStaticHelper.objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public GenSpecVersion getGenSpecVersion() {
        return genSpecVersion;
    }

    public void setGenSpecVersion(GenSpecVersion genSpecVersion) {
        this.genSpecVersion = genSpecVersion;
    }

    public List<RegionSpec> getRegionSpecList() {
        return regionSpecList;
    }

    public void setRegionSpecList(List<RegionSpec> regionSpecList) {
        this.regionSpecList = regionSpecList;
    }
}
