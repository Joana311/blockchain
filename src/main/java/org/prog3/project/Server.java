package org.prog3.project;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static ExecutorService executorService = Executors.newCachedThreadPool();
    private static final int port = 12137;

    public static void main(String[] args) {
        System.err.println("Server is starting on port " + port + "!");
        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Block> blockchain = new ArrayList<>();

        try {
            try (ServerSocket server = new ServerSocket(port)) {
                while (true) {
                    Socket connection = server.accept();
                    Client newClient = new Client(connection, clients, blockchain);
                    clients.add(newClient);
                    executorService.submit(newClient);

                    // this will be the listener, once a client adds new block, it is added to everyone
/*                    // Listen for new blocks from clients
                    newClient.onNewBlock(block -> {
                        // Validate the new block
                        if (isValid(block)) {
                            // Add the new block to the server's blockchain
                            blockchain.add(block);

                            // Broadcast the new block to all clients
                            for (Client client : clients) {
                                client.addBlock(block);
                            }
                        }
                    });
*/
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
