package ru.spbau.mit.Chat;

public class JabMessage implements Comparable<JabMessage> {
    public final String name;
    public final String message;
    public final Long time;
    public final short serverPort;

    public JabMessage(String name, String message, long time, short serverPort) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.serverPort = serverPort;
    }

    @Override
    public int compareTo(JabMessage other) {
        return time.compareTo(other.time);
    }
}
