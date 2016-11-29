package ru.spbau.mit.Chat;


import java.net.InetSocketAddress;
import java.util.Observer;

/**
 * A chat interface, we update our chat
 * (it will insert messages in the correct place - sorting by the time sent
 * (or however we want in our JabMessage class))
 *
 * We can also add Observers, get our friend's name and his address
 */
public interface Chat {
    void updateChat(JabMessage message);
    void addObserver(Observer ob);
    String getFriendName();
    InetSocketAddress getRemote();
}
