package ru.spbau.mit.Protocol;

import ru.spbau.mit.Chat.JabMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface JabProtocol {
    JabMessage sendMessage(String name, String msg, short port, DataOutputStream output) throws IOException;
    JabMessage readMessage(DataInputStream input) throws IOException;
}
