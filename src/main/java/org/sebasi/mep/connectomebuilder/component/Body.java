package org.sebasi.mep.connectomebuilder.component;

import org.apache.commons.lang3.StringUtils;

// Turns out that a brain just has too much dependency on a body, it can't run alone.
public class Body extends AbstractComponent {

    // A body has exactly one brain.
    Brain brain;

    BodyWellness bodyWellness;

    private String name;

    public Body(Brain brain) {
        this.brain = brain;
        this.bodyWellness = new BodyWellness();
        bodyWellness.initializeWithRandomValues();
    }

    public Brain getBrain() {
        return brain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void report(StringBuilder builder) {
        if (!StringUtils.isEmpty(name)) {
            builder.append("\nName: ").append(name);
        }
        bodyWellness.report(builder);
        brain.report(builder);
    }
}
