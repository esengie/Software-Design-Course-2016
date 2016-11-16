package ru.spbau.mit.Protocol;

import ru.spbau.mit.Chat.NameMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface JabProtocol {
    void sendMessage(String name, String msg, DataOutputStream output) throws IOException;
    NameMessage readMessage(DataInputStream input) throws IOException;
}
