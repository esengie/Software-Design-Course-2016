package ru.spbau.mit.Client;

import lombok.Getter;
import lombok.Setter;
import ru.spbau.mit.Protocol.JabProtocol;
import ru.spbau.mit.Protocol.JabProtocolImpl;

import java.io.*;
import java.net.Socket;

public class JabClientImpl implements JabClient {
    private boolean connected = false;
    private Socket clientSocket;
    private DataOutputStream netOut;
    private String host;
    private short port;
    @Getter @Setter private String myName;

    private JabProtocol protocol = new JabProtocolImpl();

    public JabClientImpl(String name) {
        myName = name;
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
    public void sendMessage(String msg) throws IOException {
        connect();
        protocol.sendMessage(myName, msg, netOut);
        disconnectHelper();
    }


}