package ru.spbau.mit.Client;

import java.io.IOException;

/**
 * A client interface, can connect/disconnect
 *
 * Set our name and send messages
 */
public interface JabClient {
    void connect(String hostName, short port);
    void disconnect() throws IOException;
    void sendMessage(String msg) throws IOException;

    String getMyName();
    void setMyName(String myName);
}
