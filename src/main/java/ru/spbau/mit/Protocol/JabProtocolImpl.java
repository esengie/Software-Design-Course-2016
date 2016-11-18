package ru.spbau.mit.Protocol;

import ru.spbau.mit.Chat.JabMessage;

import java.io.*;

public class JabProtocolImpl implements JabProtocol {
    @Override
    public JabMessage sendMessage(String name, String msg, short serverPort, DataOutputStream output) throws IOException {
        output.writeUTF(name);
        output.writeUTF(msg);
        output.writeShort(serverPort);
        return new JabMessage("", msg, TimeJab.getNow(), serverPort);
    }

    @Override
    public JabMessage readMessage(DataInputStream input) throws IOException {
        JabMessage ret = new JabMessage(input.readUTF(), input.readUTF(),
                TimeJab.getNow(), input.readShort());
        return ret;
    }
}
