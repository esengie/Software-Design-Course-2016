package ru.spbau.mit.Command;

import java.util.List;

public class CatCommand extends Command {
    protected CatCommand(List<Argument> a_args) {
        super(a_args);
    }
}
