package ru.spbau.mit.Exceptions;

import java.io.IOException;

public class VariableUndefinedException extends IOException {
    public VariableUndefinedException(String name) {
        super("Cannot find variable " + name);
    }
}
