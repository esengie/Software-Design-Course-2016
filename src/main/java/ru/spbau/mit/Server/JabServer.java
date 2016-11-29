package ru.spbau.mit.Server;

import java.io.IOException;

/**
 * The server side of the app
 * Can start and stop
 */
public interface JabServer {
    void start(short portNumber) throws IOException;
    void stop() throws IOException;
}
