package org.sebasi.mep.connectomebuilder.entrypoint;

public enum Command {

    createDirectoryStructure(
            "This command should be run first, on the driving computer," +
                    "\n\tto set up the files in the directory structure. After this runs, you" +
                    "\n\tcan set up NFS mounts and move regions to other machines." +
                    "\n\tBe sure to mount the diving-computer's control directory on the remote machines."),
    createDataFiles(
            "Now that the NFS mounts are set up, run this command on" +
                    "\n\tthe driving computer to make the initial data files on all the machines."),
    runRegion(
            "Now that the initial files are everywhere, on each machine" +
                    "\n\tthat has a region, run this command."),
    startTicking(
            "Now that all the regions are running, and waiting to be told" +
                    "\n\tto start ticking, run this on the driving computer to start everything going."),
    stopAll(
            "Run this command on the driving computer, when it's already ticking," +
                    "\n\tto trigger the driving computer and all the remote computers to cleanly exit."),
    stopDriver(
            "Run this command on the driving computer, when it's already ticking," +
                    "\n\tto trigger the driving computer's process to cleanly exit. Remotes will still be" +
                    "\n\trunning.");

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
        switch (this) {
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
            case stopAll:
                new CommandStop(arguments, true);
                break;
            case stopDriver:
                new CommandStop(arguments, false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
