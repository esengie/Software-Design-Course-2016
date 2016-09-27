package ru.spbau.mit.Exceptions;

public class CommandCreationError extends RuntimeException {

    public CommandCreationError(String name, Throwable cause) {
        super("Cannot create command " + name, cause);
    }
}
