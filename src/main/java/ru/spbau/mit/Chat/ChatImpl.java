package ru.spbau.mit.Chat;

import lombok.Getter;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * Gets updated by server, notifies the GUI
 */
public class ChatImpl extends Observable implements Chat {

    @Getter private String friendName;
    @Getter private final InetSocketAddress remote;

    private Set<JabMessage> history = new TreeSet<>();

    public ChatImpl(InetSocketAddress remote, String friendName){
        this.remote = remote;
        this.friendName = friendName;
    }

    @Override
    public synchronized void updateChat(JabMessage message) {
        history.add(message);
        friendName = message.name;
        setChanged();
        notifyObservers();
    }
}
