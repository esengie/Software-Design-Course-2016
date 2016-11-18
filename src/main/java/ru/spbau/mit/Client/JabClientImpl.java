package ru.spbau.mit.Client;

import lombok.Getter;
import lombok.Setter;
import ru.spbau.mit.Chat.ChatRepo;
import ru.spbau.mit.Chat.NameMessage;
import ru.spbau.mit.Protocol.JabProtocol;
import ru.spbau.mit.Protocol.JabProtocolImpl;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class JabClientImpl implements JabClient {
    private final ChatRepo repo;

    private boolean connected = false;

    private String host;
    private short port;

    private Socket clientSocket;
    private DataOutputStream netOut;

    @Getter @Setter private String myName;

    private JabProtocol protocol = new JabProtocolImpl();

    public JabClientImpl(String name, ChatRepo repo) {
        myName = name;
        this.repo = repo;
    }

    @Override
    public void connect(String hostName, short portNumber) throws IOException {
        if (connected)
            return;
        host = hostName;
        port = portNumber;
        connected = true;
    }

    private void connect() throws IOException {
        clientSocket = new Socket(host, port);
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
        NameMessage msg = protocol.sendMessage(myName, message, netOut);
        disconnectHelper();
        repo.updateChat((InetSocketAddress) clientSocket.getRemoteSocketAddress(), msg);
    }


}