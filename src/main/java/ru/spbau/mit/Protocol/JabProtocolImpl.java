package ru.spbau.mit.Protocol;

import java.io.*;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class JabProtocolImpl implements JabProtocol {
    @Override
    public void sendMessage(String name, String msg, DataOutputStream output) throws IOException {
        output.writeUTF(name);
        output.writeUTF(msg);
    }

    @Override
    public NameMessage readMessage(DataInputStream input) throws IOException {
        return new NameMessage(input.readUTF(), input.readUTF());
    }
}
