package org.sebasi.mep.connectomebuilder.entrypoint;

import com.beust.jcommander.JCommander;

public class CommandLineInvoker {

    public static void main(String... args) {

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
