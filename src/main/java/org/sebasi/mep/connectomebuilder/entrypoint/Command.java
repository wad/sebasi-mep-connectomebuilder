package org.sebasi.mep.connectomebuilder.entrypoint;

public enum Command {

    runDemo("Run a quick demo.");

    String descriptionMessage;

    Command(String descriptionMessage) {
        this.descriptionMessage = descriptionMessage;
    }

    public String getDescriptionMessage() {
        return this.name() + ": " + descriptionMessage;
    }

    public static String showUsage() {
        StringBuilder builder = new StringBuilder();
        for (Command value : values()) {
            builder.append(value.getDescriptionMessage()).append("\n");
        }
        return builder.toString();
    }

    public void invoke(Arguments arguments) {
        switch(this) {
            case runDemo:
                new CommandRunDemo(arguments);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
