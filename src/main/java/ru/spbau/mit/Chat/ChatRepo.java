package ru.spbau.mit.Chat;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRepo extends Observable {
    private Map<Integer, Chat> chats = new ConcurrentHashMap<>();

    public void updateChat(InetSocketAddress addr, JabMessage msg){
        int userID = toID(addr);

        // Only we can modify the map here,
        // using concurrency aware containers for visibility basically
        if (!chats.containsKey(userID)) {
            Chat changed = new ChatImpl(addr, msg.name);
            chats.put(userID, changed);
            // For notifying of new chats
            setChanged();
            notifyObservers(changed);
        }
        chats.get(userID).updateChat(msg);
    }

    private static int toID(InetSocketAddress socketAddress) {
        return socketAddress.hashCode();
    }

}
