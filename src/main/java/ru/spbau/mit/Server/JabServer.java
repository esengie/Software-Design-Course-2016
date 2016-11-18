package ru.spbau.mit.Server;

import java.io.IOException;
import java.util.Observer;

public interface JabServer {
    void start(short portNumber) throws IOException;
    void stop() throws IOException;
    void addObserver(Observer b);
}
