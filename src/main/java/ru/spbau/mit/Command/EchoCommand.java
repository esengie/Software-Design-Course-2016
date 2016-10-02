package ru.spbau.mit.Command;

import java.io.IOException;
import java.util.List;

/**
 * Echo - outputs its own args to outputstream
 */
class EchoCommand extends Command {
    EchoCommand(List<Argument> args) {
        super(args);
    }

    @Override
    public void run() throws IOException {
        for (Argument arg : args) {
            getOutputStream().write((arg.getContents() + " ").getBytes());
        }
        getOutputStream().write("\n".getBytes());

        flush();
    }
}
