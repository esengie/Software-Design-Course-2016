package ru.spbau.mit.Server;


import ru.spbau.mit.Chat.Chat;
import ru.spbau.mit.Chat.ChatImpl;
import ru.spbau.mit.Chat.ChatRepo;
import ru.spbau.mit.Chat.NameMessage;
import ru.spbau.mit.Protocol.JabProtocol;
import ru.spbau.mit.Protocol.JabProtocolImpl;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JabServerImpl implements JabServer {
    private static final Logger logger = Logger.getLogger(JabServer.class.getName());

    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final JabProtocol protocol = new JabProtocolImpl();
    private volatile boolean isStopped = true;
    private ServerSocket serverSocket;
    private final ChatRepo repo;

    public JabServerImpl(ChatRepo repo){
        this.repo = repo;
    }

    private class JabServerInstance implements Runnable {

        public void run() {
            try {
                while (!isStopped) {
                    Socket socket = serverSocket.accept();
                    executor.execute(() -> {
                        try {
                            DataInputStream in = new DataInputStream(socket.getInputStream());
                            NameMessage msg = protocol.readMessage(in);
                            repo.updateChat((InetSocketAddress) socket.getRemoteSocketAddress(), msg);
                            in.close();
                        } catch (IOException e) {
                            logger.log(Level.FINE, e.getMessage(), e);
                        }
                    });
                }
            } catch (SocketException e) {
                // server stop is working here
            } catch (IOException e) {
                logger.log(Level.WARNING, "Exception caught when trying to handle client", e);
            }
        }
    }

    @Override
    public void start(short portNumber) throws IOException {
        isStopped = false;
        serverSocket = new ServerSocket(portNumber);
        new Thread(new JabServerInstance()).start();
    }

    @Override
    public void stop() throws IOException {
        isStopped = false;
        serverSocket.close();
        executor.shutdownNow();
    }
}