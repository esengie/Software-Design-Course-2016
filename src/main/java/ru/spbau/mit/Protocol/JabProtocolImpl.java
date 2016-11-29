package ru.spbau.mit.Protocol;

import ru.spbau.mit.Chat.JabMessage;

import java.io.*;

/**
 * Basically it specifies that you need to send your name, your message and your port,
 * so the other guy knows your name and your receiving port
 * (redundancy here, but doesn't really matter)
 *
 * When we send we return a message we've sent for homogeneity
 */
public class JabProtocolImpl implements JabProtocol {
    @Override
    public JabMessage sendMessage(String name, String msg, short serverPort, DataOutputStream output) throws IOException {
        output.writeUTF(name);
        output.writeUTF(msg);
        output.writeShort(serverPort);
        return new JabMessage("", msg, GetNow.getNow(), serverPort);
    }

    @Override
    public JabMessage readMessage(DataInputStream input) throws IOException {
        JabMessage ret = new JabMessage(input.readUTF(), input.readUTF(),
                GetNow.getNow(), input.readShort());
        return ret;
    }
}
