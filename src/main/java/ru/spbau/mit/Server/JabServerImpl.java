package ru.spbau.mit.Server;


import ru.spbau.mit.Chat.ChatRepo;
import ru.spbau.mit.Chat.JabMessage;
import ru.spbau.mit.Protocol.JabProtocol;
import ru.spbau.mit.Protocol.JabProtocolImpl;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Has some threads doing the messaging job, otherwise is pretty simple:
 * has a volatile boolean serving as a signal to stop
 *
 * The workers use the server part of the protocol and log messages received to the ChatRepo
 */
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
                            JabMessage msg = protocol.readMessage(in);
                            repo.updateChat(new InetSocketAddress(socket.getInetAddress(),
                                    msg.serverPort), msg);
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