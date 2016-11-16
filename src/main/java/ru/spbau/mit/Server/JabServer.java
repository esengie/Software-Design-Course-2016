package ru.spbau.mit.Server;

import java.io.IOException;
import java.util.List;

public interface JabServer {
    void start(short portNumber) throws IOException;
    void stop() throws IOException;
    List<Talker> getUsers();
    List<String> getMessages(Talker user);
}
