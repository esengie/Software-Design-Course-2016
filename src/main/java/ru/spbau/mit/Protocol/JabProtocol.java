package ru.spbau.mit.Protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface JabProtocol {
    void sendMessage(String name, String msg, DataOutputStream output) throws IOException;
    NameMessage readMessage(DataInputStream input) throws IOException;
}
