package ru.spbau.mit.Chat;

public class NameMessage implements Comparable<NameMessage> {
    public final String name;
    public final String message;
    public final Long time;

    public NameMessage(String name, String message, long time) {
        this.name = name;
        this.message = message;
        this.time = time;
    }

    @Override
    public int compareTo(NameMessage other) {
        return time.compareTo(other.time);
    }
}
