package org.prog3.project;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client implements Runnable {
    Socket connection;
    ServerSocket serverSocket;
    BufferedReader in;
    BufferedWriter out;
    static ArrayList<Client> clients;
    static ExecutorService executorService = Executors.newCachedThreadPool();
    private static final int port = 12137;

    public Client(Socket connection, ArrayList<Client> clients, int serverPort) throws IOException {
        this.connection = connection;
        Client.clients = clients;
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

        // create a ServerSocket to accept incoming connections
        this.serverSocket = new ServerSocket(serverPort);
        new Thread(() -> {
            while (true) {
                try {
                    // accept a new client and add it to the list
                    Socket clientSocket = serverSocket.accept();
                    Client client = new Client(clientSocket, clients, serverPort);
                    clients.add(client);
                    new Thread(client).start();
                    executorService.submit(client);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    
    @Override
    public void run() {
        String line;
//        try {
//            while ((line = in.readLine()) != null) {
//                System.out.println("some message was sent");
//                String forEveryone = line;
//                clients.stream().filter(client -> client != this).forEach(client -> client.send(forEveryone));
//            }
//        } catch (Exception e) {
//            System.out.println("Protocol violation! " + e.getMessage());
//        }
    }

//    public void send(String message) {
//        try {
//            out.write(message);
//            out.newLine();
//            out.flush();
//        } catch (Exception e) {
//            System.out.println("Socket broke");
//        }
//    }

    public static void main(String[] args) {
        clients = new ArrayList<>();
        try {
            try (ServerSocket server = new ServerSocket(port)) {
                while (true) {
                    Socket connection = server.accept();
                    Client newClient = new Client(connection, clients, port);
                    clients.add(newClient);
                    executorService.submit(newClient);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}