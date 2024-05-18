package org.prog3.project.Protocols;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Message.Message;
import org.prog3.project.Network.NetworkManager;

@Getter
@Setter
public class BlockchainProtocol implements Protocol {
    private NetworkManager networkManager;

    public enum Type {
        PING, PONG, REQUEST, RESPONSE
    }

    private Type type;

    public BlockchainProtocol(Type type, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.type = type;
    }

    @Override
    public void digest(Message message) {

    }
}
