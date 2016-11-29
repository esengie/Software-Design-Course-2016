package ru.spbau.mit.Protocol;

import ru.spbau.mit.Chat.JabMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A protocol class specifies how to send messages (along with a name) and receive them
 *
 * Each function returns a message object, containing the date received/sent and the contents
 */
public interface JabProtocol {
    JabMessage sendMessage(String name, String msg, short port, DataOutputStream output) throws IOException;
    JabMessage readMessage(DataInputStream input) throws IOException;
}
