package ru.spbau.mit.Server;


import ru.spbau.mit.Chat.Chat;
import ru.spbau.mit.Chat.ChatImpl;
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

public class JabServerImpl extends Observable implements JabServer {
    private static final Logger logger = Logger.getLogger(JabServer.class.getName());

    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final JabProtocol protocol = new JabProtocolImpl();
    private volatile boolean isStopped = true;
    private ServerSocket serverSocket;
    private Map<Integer, Chat> chats = new ConcurrentHashMap<>();

    private class JabServerInstance implements Runnable {

        public void run() {
            try {
                while (!isStopped) {
                    Socket socket = serverSocket.accept();
                    executor.execute(() -> {
                        try {
                            DataInputStream in = new DataInputStream(socket.getInputStream());
                            NameMessage msg = protocol.readMessage(in);
                            int userID = toID((InetSocketAddress) socket.getRemoteSocketAddress());
                            // Only we can modify the map here,
                            // using concurrency aware containers for visibility basically
                            if (!chats.containsKey(userID)) {
                                Chat changed = new ChatImpl(userID, msg.name);
                                chats.put(userID, changed);
                                // For notifying of new chats
                                setChanged();
                                notifyObservers(changed);
                            }
                            chats.get(userID).updateChat(msg);
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

    private static int toID(InetSocketAddress socketAddress) {
        return socketAddress.hashCode();
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

    public List<Chat> getChats() {
        return new ArrayList<>(chats.values());
    }

}