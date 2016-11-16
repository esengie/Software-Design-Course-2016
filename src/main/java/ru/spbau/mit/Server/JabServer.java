package ru.spbau.mit.Server;

import ru.spbau.mit.Chat.Chat;

import java.io.IOException;
import java.util.List;

public interface JabServer {
    void start(short portNumber) throws IOException;
    void stop() throws IOException;
    List<Chat> getChats();
}
