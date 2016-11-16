package ru.spbau.mit.Protocol;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.Chat.NameMessage;

import java.io.*;

import static org.junit.Assert.assertEquals;


public class JabProtocolImplTest {

    private ByteArrayOutputStream outContent;
    private DataInputStream inContent;
    private final JabProtocol prot = new JabProtocolImpl();

    @Before
    public void setUpStreams() throws IOException {
        outContent = new ByteArrayOutputStream();
    }

    @Test
    public void sendMsg() throws Exception {
        prot.sendMessage("Anon", "asd", new DataOutputStream(outContent));

        inContent = new DataInputStream(new ByteArrayInputStream(outContent.toByteArray()));
        assertEquals("Anon", inContent.readUTF());
        assertEquals("asd", inContent.readUTF());
    }

    @Test
    public void readMsg() throws Exception {
        DataOutputStream b = new DataOutputStream(outContent);
        b.writeUTF("Anon");
        b.writeUTF("asd");

        inContent = new DataInputStream(new ByteArrayInputStream(outContent.toByteArray()));

        NameMessage msg = prot.readMessage(inContent);

        assertEquals("Anon", msg.name);
        assertEquals("asd", msg.message);
    }


}