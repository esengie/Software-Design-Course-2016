package ru.spbau.mit.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface Client {
    void connect(String hostName, short port) throws IOException;
    void disconnect() throws IOException;
    void sendMessage(String msg) throws IOException;
}
