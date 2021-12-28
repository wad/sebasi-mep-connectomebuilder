package org.sebasi.mep.connectomebuilder.component;

// Turns out that a brain just has too much dependency on a body, it can't run alone.
public class Body {

    Brain brain;

    public Body(Brain brain) {
        this.brain = brain;
    }

    public Brain getBrain() {
        return brain;
    }
}
