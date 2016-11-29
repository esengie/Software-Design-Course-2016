package ru.spbau.mit.Chat;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A chat repo class, Observable - to notify of new chats
 * <p>
 * Keeps a map of InetSocketAddress to Chat
 */
public class ChatRepo extends Observable {
    private Map<Integer, Chat> chats = new ConcurrentHashMap<>();

    /**
     * Creates a new chat if the need arises and updates the Observers of the repo
     *
     * Updates the observers of the chat
     *
     * @param addr of the sender
     * @param msg of the sender
     */
    public synchronized void updateChat(InetSocketAddress addr, JabMessage msg) {
        int userID = toID(addr);

        if (!chats.containsKey(userID)) {
            Chat changed = new ChatImpl(addr, msg.name);
            chats.put(userID, changed);
            setChanged();
            notifyObservers(changed);
        }

        chats.get(userID).updateChat(msg);
    }

    private static int toID(InetSocketAddress socketAddress) {
        return socketAddress.hashCode();
    }

}
