package ru.spbau.mit.Server;

import java.io.IOException;

public interface JabServer {
    void start(short portNumber) throws IOException;
    void stop() throws IOException;
}
