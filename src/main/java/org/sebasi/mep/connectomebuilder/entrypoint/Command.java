package org.sebasi.mep.connectomebuilder.entrypoint;

public enum Command {

    // This command should be run first, on the driving computer, to set up the files in the directory structure.
    // After this runs, you can apply NFS mounts to move regions to other machines.
    createDirectoryStructure(""),

    // Now that the NFS mounts are set up, run this command on the driving computer to make the initial data files on all the machines.
    createDataFiles(""),

    // Now that the initial files are everywhere, on each machine that has a region, run this command.
    runRegion(""),

    // Now that all the regions are running, and waiting to be told to start ticking, run this on the driving computer
    // to start everything going.
    startTicking("");

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
            case createDirectoryStructure:
                new CommandCreateDirectoryStructure(arguments);
                break;
            case createDataFiles:
                new CommandCreateDataFiles(arguments);
                break;
            case runRegion:
                new CommandRunRegion(arguments);
                break;
            case startTicking:
                new CommandStartTicking(arguments);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
