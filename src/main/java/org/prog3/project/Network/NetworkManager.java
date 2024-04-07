package org.prog3.project.Network;

import org.prog3.project.Message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkManager {
    HashMap<String, Peer> peers;
    Queue<Message> incoming;
    Queue<Message> outgoing;
    ExecutorService networkPool;

    // TODO: config this constructor
    public NetworkManager() {
        peers = new HashMap<>();
        incoming = new LinkedList<>();
        outgoing = new LinkedList<>();
        networkPool = Executors.newCachedThreadPool();

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(12137)) {
                while (true) {
                    Peer peer = new Peer(serverSocket.accept(), this);
                    networkPool.submit(peer);
                    peers.put("" + peer.hashCode(), peer);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String message = scanner.next();
                peers.values().parallelStream().forEach(peer -> peer.send(message));
            }
        }).start();

    }

    public void connect(String address, int port) {
        try {
            Peer p = new Peer(new Socket(address, port), this);
            networkPool.submit(p);
            peers.put("" + p.hashCode(), p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void broadcast(String msg) {
        peers.values().parallelStream().forEach(peer -> peer.send(msg));
    }

    // TODO: function to create message to be sent

    // TODO: function for verifying message
}
