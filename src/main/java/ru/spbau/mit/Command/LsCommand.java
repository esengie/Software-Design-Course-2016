package ru.spbau.mit.Command;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Ls command prints file list in directory.
 * If no args where given, than it prints files in current directory.
 * Else it considers every arg as directory name and prints file for every one.
 */
class LsCommand extends Command {

    public LsCommand(List<String> args) {
        super(args);
    }

    @Override
    public void run() throws IOException {
        List<String> dirNames = args.isEmpty() ? Collections.singletonList(System.getProperty("user.dir")) : args;
        for (String name: dirNames) {

            File[] files = new File(name).getAbsoluteFile().listFiles();
            if (files == null) {
                getOutputStream().write(("Can't list " + name + "\n").getBytes());
                continue;
            }
            Arrays.sort(files);
            if (dirNames.size() > 1) {
                getOutputStream().write((name + ":\n").getBytes());
            }
            for (File file: files) {
                getOutputStream().write((file.getName() + " ").getBytes());
            }
            getOutputStream().write("\n".getBytes());
        }

        flush();
    }
}
