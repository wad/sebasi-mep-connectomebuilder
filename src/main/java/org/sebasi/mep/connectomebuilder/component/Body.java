package org.sebasi.mep.connectomebuilder.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.sebasi.mep.connectomebuilder.util.FileUtil;
import org.sebasi.mep.connectomebuilder.util.GlobalStaticHelper;

public class Body extends AbstractComponent {

    private String name;
    BodyWellness bodyWellness;

    public Body(String name) {
        this.name = name;
        this.bodyWellness = new BodyWellness();
        bodyWellness.initializeWithRandomValues();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BodyWellness getBodyWellness() {
        return bodyWellness;
    }

    @Override
    public void report(StringBuilder builder) {
        if (!StringUtils.isEmpty(name)) {
            builder.append("\nName: ").append(name);
        }
        bodyWellness.report(builder);
    }

    public void writeInitialDataFiles(String einDirectory) {
        try {
            FileUtil.writeBody(
                    einDirectory,
                    GlobalStaticHelper.objectMapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problem serializing body into json: " + e.getMessage(), e);
        }
    }
}
