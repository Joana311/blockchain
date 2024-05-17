package org.prog3.project;

import org.prog3.project.Network.NetworkManager;
import org.prog3.project.Network.Peer;
import org.prog3.project.Security.Crypto;
import org.prog3.project.Utilities.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // TODO: toggle the executorService, not necessary to use it this early
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            Constants constants = new Constants();
            Crypto cr = new Crypto();   // create keyPair, used later
            NetworkManager networkManager = new NetworkManager(constants, cr);
            // connecting normal node, not "trusted"
            if (!networkManager.isTrusted()) {
                Socket socket;
                try {
                    socket = new Socket(constants.HOST, constants.PORT);
                    executorService.submit(new Thread(new Peer(socket, networkManager)));
                } catch (IOException e) {
                    System.out.println("Client failed to connect\n");
                    throw new RuntimeException(e);
                }
                // TODO: what else? Peer discovery? Trusted? Send messages?
            }
            // booting from trusted node (only once - the first time)
            try (ServerSocket serverSocket = new ServerSocket(constants.PORT)) {
                System.out.println("Server listening on port: " + serverSocket.getLocalPort() + "\n");
                while (true) executorService.submit(new Thread(new Peer(serverSocket.accept(), networkManager)));
            } catch (IOException e) {
                System.out.println("Server failed to start\n");
                throw new RuntimeException(e);
            }
        } // executorService automatically calls the shutdown method (JDK 21)
    }
}
