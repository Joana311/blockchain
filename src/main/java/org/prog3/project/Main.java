package org.prog3.project;

import org.prog3.project.Network.NetworkManager;
import org.prog3.project.Network.Peer;
import org.prog3.project.Utilities.Constants;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        // TODO: Trusted node needs to be present, at least for booting the network
        NetworkManager networkManager = new NetworkManager();
        Socket socket;
        try {
            socket = new Socket(Constants.HOST, Constants.PORT);
            Peer peer = new Peer(socket, networkManager);
            peer.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
