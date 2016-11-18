package ru.spbau.mit.Chat;

import lombok.Getter;
import ru.spbau.mit.Protocol.TimeJab;

import java.util.*;

/**
 * Gets updated by server, notifies the GUI
 */
public class ChatImpl extends Observable implements Chat {

    @Getter private String friendName;
    @Getter private final int id;

    private Set<NameMessage> history = new TreeSet<>();

    public ChatImpl(int id, String friendName){
        this.id = id;
        this.friendName = friendName;
    }

    @Override
    public synchronized void addMessage(String name, String message){
        history.add(new NameMessage(name, message, TimeJab.getNow()));
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void updateChat(NameMessage message) {
        history.add(message);
        friendName = message.name;
        setChanged();
        notifyObservers();
    }
}
