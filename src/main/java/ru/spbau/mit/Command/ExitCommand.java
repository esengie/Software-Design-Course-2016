package ru.spbau.mit.Command;

import java.io.IOException;
import java.util.List;

public class ExitCommand extends Command {
    ExitCommand(List<Argument> a_args) {
        super(a_args);
    }

    @Override
    public void run() throws IOException {
        System.exit(0);
    }
}
