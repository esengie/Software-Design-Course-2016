package ru.spbau.mit.Chat;


import java.util.Observer;

public interface Chat {
    void addMessage(String name, String message);
    void updateChat(NameMessage message);
    void addObserver(Observer ob);
}
