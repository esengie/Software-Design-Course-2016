package ru.spbau.mit.Client;

import java.io.IOException;

public interface JabClient {
    void connect(String hostName, short port) throws IOException;
    void disconnect() throws IOException;
    void sendMessage(String msg) throws IOException;

    String getMyName();
    void setMyName(String myName);
}
