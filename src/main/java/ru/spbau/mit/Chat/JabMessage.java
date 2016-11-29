package ru.spbau.mit.Chat;

/**
 * A message of our system, knows the name of the sender, the message, the time sent
 *
 * and the server port of the sender (so we know how to return an answer)
 *
 * Can compare itself to other messages (by time sent)
 */
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
