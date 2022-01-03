package org.sebasi.mep.connectomebuilder.entrypoint;

import com.beust.jcommander.JCommander;
import org.sebasi.mep.connectomebuilder.util.TextOutput;

public class EntryPoint {

    public static void main(String... args) {

        TextOutput.showMessage("\n\nThis is the MCB! Note that it's a work in progress. Assume nothing works.\n\n");

        Arguments arguments = new Arguments();
        JCommander jCommander = JCommander
                .newBuilder()
                .addObject(arguments)
                .build();
        jCommander.parse(args);

        Command command = arguments.getCommand();
        if (command == null || arguments.isHelp()) {
            System.out.println("Here are the available arguments:");
            jCommander.usage();
            System.out.println("Here are the descriptions of the various commands:");
            System.out.println(Command.showUsage());
            return;
        }

        command.invoke(arguments);
    }
}
