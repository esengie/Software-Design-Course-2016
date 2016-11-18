package ru.spbau.mit.Chat;


import java.util.Observer;

public interface Chat {
    void updateChat(NameMessage message);
    void addMessage(String name, String message);
    void addObserver(Observer ob);
    String getFriendName();
    int getId();
}
