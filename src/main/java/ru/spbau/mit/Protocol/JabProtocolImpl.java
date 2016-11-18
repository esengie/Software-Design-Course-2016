package ru.spbau.mit.Protocol;

import ru.spbau.mit.Chat.NameMessage;

import java.io.*;
import java.util.Calendar;

public class JabProtocolImpl implements JabProtocol {
    @Override
    public NameMessage sendMessage(String name, String msg, DataOutputStream output) throws IOException {
        output.writeUTF(name);
        output.writeUTF(msg);
        return new NameMessage(name, msg, TimeJab.getNow());
    }

    @Override
    public NameMessage readMessage(DataInputStream input) throws IOException {
        return new NameMessage(input.readUTF(), input.readUTF(), TimeJab.getNow());
    }
}
