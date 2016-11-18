package ru.spbau.mit.Server;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.Protocol.JabProtocol;
import ru.spbau.mit.Protocol.JabProtocolImpl;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class JabServerImplTest {

    private ByteArrayOutputStream outContent;
    private DataInputStream inContent;
    private Socket sock;
    private final JabProtocol prot = new JabProtocolImpl();
    private final short portNumber = 1234;

    @Before
    public void setUpStreams() throws IOException {
        outContent = new ByteArrayOutputStream();
    }


    @Test
    public void Test() throws Exception {
//        JabServer server = new JabServerImpl();
//        server.start(portNumber);
//
//        Thread.sleep(200);
//        JabClient client = new JabClientImpl();
//        client.connect("localhost", portNumber);
//
//        List<RemoteFile> lst = client.executeList(dir.getAbsolutePath());
//
//        assertEquals(f2.getAbsolutePath(), lst.get(0).path);
//
//        client.executeGet(lst.get(0).path, outContent);
//
//        assertEquals(F2DIR, outContent.toString());
    }

}