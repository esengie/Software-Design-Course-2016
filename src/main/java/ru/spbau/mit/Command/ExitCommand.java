package ru.spbau.mit.Command;

import java.io.IOException;
import java.util.List;


/**
 * System exit - just exits
 */
class ExitCommand extends Command {
    ExitCommand(List<Argument> args) {
        super(args);
    }

    @Override
    public void run() throws IOException {
        System.exit(0);
    }
}
