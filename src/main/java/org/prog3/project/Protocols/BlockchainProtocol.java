package org.prog3.project.Protocols;

import org.prog3.project.Message.Message;
import org.prog3.project.Network.NetworkManager;

public class BlockchainProtocol implements Protocol {
    NetworkManager networkManager;

    public BlockchainProtocol(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public void digest(Message message) {
        // TODO: do something useful here
    }
}
