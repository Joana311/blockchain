package org.prog3.project.Protocol;

import org.prog3.project.Network.Message;
import org.prog3.project.Network.NetworkManager;

public class BlockchainProtocol implements Protocol {
    NetworkManager networkManager = new NetworkManager();

    @Override
    public void digest(Message message) {
        // TODO: do something useful here
    }
}
