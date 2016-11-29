package ru.spbau.mit.Client;

import lombok.Getter;
import lombok.Setter;
import ru.spbau.mit.Chat.ChatRepo;
import ru.spbau.mit.Chat.JabMessage;
import ru.spbau.mit.Protocol.JabProtocol;
import ru.spbau.mit.Protocol.JabProtocolImpl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Implements the protocol, is stateless in a sense that it connects and disconnects every time,
 * logs every message to the ChatRepo class
 */
public class JabClientImpl implements JabClient {
    private final ChatRepo repo;
    private final short serverPort;

    private boolean connected = false;

    private InetSocketAddress remoteServer;
    private DataOutputStream netOut;

    @Getter
    @Setter
    private String myName;

    private JabProtocol protocol = new JabProtocolImpl();

    public JabClientImpl(String name, short serverPort, ChatRepo repo) {
        myName = name;
        this.serverPort = serverPort;
        this.repo = repo;
    }

    @Override
    public void connect(String hostName, short portNumber) {
        if (connected)
            return;
        remoteServer = new InetSocketAddress(hostName, portNumber);
        connected = true;
    }

    private void connect() throws IOException {
        Socket clientSocket = new Socket();
        // Don't know why this is laggy
        clientSocket.connect(remoteServer, 100);
        netOut = new DataOutputStream(clientSocket.getOutputStream());
    }

    @Override
    public void disconnect() throws IOException {
        if (!connected)
            return;
        connected = false;
        disconnectHelper();
    }

    private void disconnectHelper() throws IOException {
        netOut.close();
    }

    @Override
    public void sendMessage(String message) throws IOException {
        connect();
        JabMessage msg = protocol.sendMessage(myName, message, serverPort, netOut);
        disconnectHelper();
        repo.updateChat(remoteServer, msg);
    }


}