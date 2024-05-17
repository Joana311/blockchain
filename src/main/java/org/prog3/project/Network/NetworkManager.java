package org.prog3.project.Network;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Message.Message;
import org.prog3.project.Message.MessageHeader;
import org.prog3.project.Security.Crypto;
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
    private Crypto cr;
    Queue<Message> queue = new PriorityQueue<>();

    public NetworkManager(Constants constants, Crypto cr) {
        this.cr = cr;
        peers = new HashMap<>();
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        if (ip.equals(constants.HOST))
            this.isTrusted = true;
        // TODO: discover peers here, and with the thread bellow process if message is incoming

        new Thread(() -> {
            // TODO: pop message from the stack, verify it, and if true, digest it (protocol based)
            if (verifyMessage(Objects.requireNonNull(queue.poll()))){
                System.err.println("signature okay");
                // TODO: based on the protocol, do something
            } else {
                // TODO: signature does not match, disconnect the node
                System.err.println("message signature not match. Disconnecting");
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
        return Objects.equals(cr.applySHA256(message.getBody() +
                message.getHeader().getTimestamp() +
                message.getHeader().getProtocol() +
                message.getHeader().getPublicKey()), message.getHeader().getSignature());
    }
}
