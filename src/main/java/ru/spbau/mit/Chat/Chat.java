package ru.spbau.mit.Chat;


public interface Chat {
    void addMessage(String name, String message);
    void updateChat(NameMessage message);
}
