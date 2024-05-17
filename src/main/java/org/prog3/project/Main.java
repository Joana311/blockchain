package org.prog3.project;

import org.prog3.project.Network.NetworkManager;
import org.prog3.project.Network.Peer;
import org.prog3.project.Utilities.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Constants constants = new Constants();
        NetworkManager networkManager = new NetworkManager(constants);
        // connecting normal node, not "trusted"
        if (!networkManager.isTrusted()) {
            Socket socket;
            try {
                socket = new Socket(constants.HOST, constants.PORT);
            } catch (IOException e) {
                System.out.println("Client failed to connect\n");
                throw new RuntimeException(e);
            }
            new Thread(new Peer(socket, networkManager)).start();
            // TODO: what else? Peer discovery? Trusted? Send messages?
        }
        // booting from trusted node (only once - the first time)
        try (ServerSocket serverSocket = new ServerSocket(constants.PORT)) {
            System.out.println("Server listening on port: " + serverSocket.getLocalPort() + "\n");
            while (true) new Thread(new Peer(serverSocket.accept(), networkManager)).start();
        } catch (IOException e) {
            System.out.println("Server failed to start\n");
            throw new RuntimeException(e);
        }
    }
}
