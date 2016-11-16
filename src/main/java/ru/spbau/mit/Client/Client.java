package ru.spbau.mit.Client;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface Client {
    void connect(String hostName, short port) throws IOException;
    void disconnect() throws IOException;
    String executeList(String path) throws IOException;
    void executeGet(String path, OutputStream out) throws IOException;
}
