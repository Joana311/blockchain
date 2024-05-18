package org.prog3.project.Protocols;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Message.Message;
import org.prog3.project.Network.NetworkManager;

@Getter
@Setter
public class BlockchainProtocol implements Protocol {
    private NetworkManager networkManager;

    public BlockchainProtocol(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public void digest(Message message) {
        // TODO: do something useful here
    }
}
