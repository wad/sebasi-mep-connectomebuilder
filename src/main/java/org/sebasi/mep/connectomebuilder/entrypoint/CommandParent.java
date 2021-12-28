package org.sebasi.mep.connectomebuilder.entrypoint;

abstract public class CommandParent {

    protected Arguments arguments;

    protected CommandParent(Arguments arguments) {
        this.arguments = arguments;
    }
}
