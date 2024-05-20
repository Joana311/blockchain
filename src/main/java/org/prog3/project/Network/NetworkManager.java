package org.prog3.project.Network;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Message.Message;
import org.prog3.project.Message.MessageHeader;
import org.prog3.project.Protocols.BlockchainProtocol;
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
    private Queue<Message> queue = new PriorityQueue<>();
    private BlockchainProtocol blockchainProtocol;

    public NetworkManager(Constants constants, Crypto cr) {
        this.cr = cr;
        this.peers = new HashMap<>();
        this.blockchainProtocol = new BlockchainProtocol(this);
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        if (ip.equals(constants.HOST)) this.isTrusted = true;
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // TODO: maybe use locking to access the queue?
                while (!queue.isEmpty()) {
                    Message message = queue.poll();
                    switch (message.getHeader().getProtocol().getType()) {
                        case PING, REQUEST:
                            blockchainProtocol.digest(message);
                            break;
                        case PONG, RESPONSE:
                            break;
                        default:
                            System.out.println("ERROR: Protocol violation! Disconnecting...");
                            // TODO: maybe disconnect the node that sends faulty messages
                    }
                }
            }
        }).start();
    }

    public Message createMessage(MessageHeader header, String body) {
        return new Message(header, body);
    }

    public boolean verifyMessage(Message message) {
        return Objects.equals(cr.applySHA256(message.getBody() + message.getHeader().getTimestamp() + message.getHeader().getProtocol() + message.getHeader().getPublicKey()), message.getHeader().getSignature());
    }
}
