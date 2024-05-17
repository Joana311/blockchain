package org.prog3.project.Network;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Message.Message;
import org.prog3.project.Message.MessageHeader;
import org.prog3.project.Utilities.Constants;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Getter
@Setter
public class NetworkManager {
    private String ip;
    private boolean isTrusted = false;
    private HashMap<String, Peer> peers;

//    private Crypto crypto;
//    private BlockingQueue<Message> messageQueue = new ArrayBlockingQueue<>(1024);
//    private HashMap<String, PeerInfo> knownNodes;
//    private PeerDiscovery peerDiscovery;

    // TODO: config this constructor
    // TODO: network managed needs to hear for incoming messages
    public NetworkManager(Constants constants) {
        peers = new HashMap<>();
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        if (ip.equals(constants.HOST))
            this.isTrusted = true;
        // TODO: discover peers here, and with the thread bellow process if message is incoming

        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO: handle messages here
            }
        }).start();
    }

    public void broadcast(String msg) {
        peers.values().parallelStream().forEach(peer -> peer.send(msg));
    }

    public Message createMessage(MessageHeader header, String body) {
        // TODO: verify the message - set signature

        return new Message(header, body);
    }

    public boolean verifyMessage(Message message) {
        // TODO: make function in crypto to verify the message, but first need to set the signature
        return true;
    }
}
