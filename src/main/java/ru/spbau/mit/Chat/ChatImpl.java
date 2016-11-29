package ru.spbau.mit.Chat;

import lombok.Getter;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * Gets updated by the repo, notifies its' Observers
 *
 * Keeps the address of the remote and the friend's name
 */
class ChatImpl extends Observable implements Chat {

    @Getter private String friendName;
    @Getter private final InetSocketAddress remote;

    ChatImpl(InetSocketAddress remote, String friendName){
        this.remote = remote;
        this.friendName = friendName;
    }

    @Override
    public synchronized void updateChat(JabMessage message) {
        if (!message.name.equals("")) {
            friendName = message.name;
        }
        setChanged();
        notifyObservers(message);
    }
}
