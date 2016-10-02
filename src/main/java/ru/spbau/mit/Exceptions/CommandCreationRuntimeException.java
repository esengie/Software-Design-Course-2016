package ru.spbau.mit.Exceptions;

public class CommandCreationRuntimeException extends RuntimeException {

    public CommandCreationRuntimeException(String name, Throwable cause) {
        super("Cannot create command " + name, cause);
    }
}
