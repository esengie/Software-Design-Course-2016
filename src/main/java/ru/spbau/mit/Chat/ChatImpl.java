package ru.spbau.mit.Chat;

import lombok.Getter;

import java.util.Calendar;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

/**
 * Gets updated by server, notifies the GUI
 */
public class ChatImpl extends Observable implements Chat {

    @Getter private String friend;

    private Map<Long, NameMessage> history = new TreeMap<>();

    public final int id;

    public ChatImpl(int id, String friend){
        this.id = id;
        this.friend = friend;
    }

    private static long getNow(){
        return Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public synchronized void addMessage(String name, String message) {
        history.put(getNow(), new NameMessage(name, message));
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void updateChat(NameMessage message) {
        history.put(getNow(), message);
        friend = message.name;
        setChanged();
        notifyObservers();
    }
}
