package ru.spbau.mit.Command;

import java.io.IOException;
import java.util.List;

/**
 * Echo - outputs its own args to outputstream
 */
class EchoCommand extends Command {
    EchoCommand(List<String> args) {
        super(args);
    }

    @Override
    public void run() throws IOException {
        for (String arg : args) {
            getOutputStream().write((arg + " ").getBytes());
        }
        getOutputStream().write("\n".getBytes());

        flush();
    }
}
