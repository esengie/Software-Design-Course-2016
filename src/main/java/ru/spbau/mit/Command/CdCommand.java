package ru.spbau.mit.Command;


import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Change current directory to first argument.
 * If no arguments were passed it changes to home directory.
 */
class CdCommand extends Command {

    public CdCommand(List<String> args) {
        super(args);
    }

    @Override
    public void run() throws IOException {
        String directoryName = args.size() < 1 ? System.getProperty("user.home") : args.get(0);

        File directory = new File(directoryName).getAbsoluteFile();
        if (!directory.isDirectory()) {
            getOutputStream().write((directoryName + " is not directory.\n").getBytes());
            return;
        }

        String result = System.setProperty("user.dir", directory.getAbsolutePath());
        if (result == null) {
            getOutputStream().write(("Can't change directory to " + directoryName + "\n").getBytes());
        }

        flush();
    }
}
