package ru.spbau.mit.Server;


import ru.spbau.mit.Protocol.JabProtocol;
import ru.spbau.mit.Protocol.JabProtocolImpl;
import ru.spbau.mit.Protocol.NameMessage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JabServerImpl implements JabServer {
    private static final Logger logger = Logger.getLogger(JabServer.class.getName());

    private final ExecutorService executor = Executors.newFixedThreadPool(10);
    private final JabProtocol protocol = new JabProtocolImpl();
    private volatile boolean isStopped = true;
    private ServerSocket serverSocket;
    private Map<Integer, Talker> userNames = new ConcurrentHashMap<>();
    private Map<Integer, BlockingQueue<String>> userMessages = new ConcurrentHashMap<>();

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
                            if (!userMessages.containsKey(userID)){
                                userMessages.put(userID, new LinkedBlockingQueue<>());
                            }
                            userNames.put(userID, new Talker(userID, msg.name));
                            userMessages.get(userID).add(msg.message);
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

    @Override
    public List<Talker> getUsers() {
        return new ArrayList<>(userNames.values());
    }

    @Override
    public List<String> getMessages(Talker user) {
        return null;
    }
}