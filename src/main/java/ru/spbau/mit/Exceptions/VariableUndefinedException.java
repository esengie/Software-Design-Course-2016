package ru.spbau.mit.Exceptions;

public class VariableUndefinedException extends Exception {
    public VariableUndefinedException(String name) {
        super("Cannot find variable " + name);
    }
}
