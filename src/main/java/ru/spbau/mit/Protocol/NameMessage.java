package ru.spbau.mit.Protocol;

public class NameMessage {
    public final String name;
    public final String message;

    public NameMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
