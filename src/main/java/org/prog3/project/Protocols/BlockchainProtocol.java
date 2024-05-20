package org.prog3.project.Protocols;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Message.Message;
import org.prog3.project.Network.NetworkManager;
import org.prog3.project.Network.Peer;

@Getter
@Setter
public class BlockchainProtocol implements Protocol {
    private NetworkManager networkManager;
    private Type type;

    public enum Type {
        PING, PONG, REQUEST, RESPONSE
    }

    public BlockchainProtocol(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public void digest(Message message) {
        // TODO: based on which protocol is used in message (the received one), if PING initialize peerDoscovery
        // If request, create message and send it to the peer of the message
        // how idk yet, but step closer
    }

    @Override
    public void reply(Message message, Peer peer) {
        peer.send(message);
    }
}
