package ru.spbau.mit.Chat;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Gets updated by server, notifies the GUI
 */
public class ChatImpl implements Chat {

    @Getter private String nameTo;

    private List<NameMessage> history = new ArrayList<>();

    public final int id;

    public ChatImpl(int id, String nameTo){
        this.id = id;
        this.nameTo = nameTo;
    }

//    void subscribe(){
//        notify()
//    }

    @Override
    public synchronized void addMessage(String name, String message) {
        history.add(new NameMessage(name, message));
    }

    @Override
    public synchronized void updateChat(NameMessage message) {
        history.add(message);
        nameTo = message.name;
    }
}
