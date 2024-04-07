package org.prog3.project.protocols;

import org.prog3.project.Network.Message.Message;
import org.prog3.project.Network.NetworkManager;

public class BlockchainProtocol implements Protocol {
    NetworkManager networkManager = new NetworkManager();

    @Override
    public void digest(Message message) {
        // TODO: do something useful here
    }
}
