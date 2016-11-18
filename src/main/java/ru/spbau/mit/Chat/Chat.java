package ru.spbau.mit.Chat;


import java.net.InetSocketAddress;
import java.util.Observer;

public interface Chat {
    void updateChat(JabMessage message);
    void addObserver(Observer ob);
    String getFriendName();
    InetSocketAddress getRemote();
}
