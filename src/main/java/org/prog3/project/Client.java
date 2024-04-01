package org.prog3.project;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client implements Runnable {
    Socket connection;
    BufferedReader in;
    BufferedWriter out;
    ArrayList<Client> clients;
    ArrayList<Block> blockchain;
    private String id;
    Wallet wallet = new Wallet();
    public Client(Socket connection, ArrayList<Client> clients, ArrayList<Block> blockchain) throws IOException {
        this.connection = connection;
        this.clients = clients;
        this.blockchain = blockchain;
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
//        Scanner scan = new Scanner(System.in);
//        this.id = scan.nextLine();
//        scan.close();
    }


//    public void sendFunds(Client receiver, float amount) {
//        Transaction transaction = this.wallet.createTransaction(receiver.wallet.getPublicKey(), amount);
//        if (transaction != null) {
//            Block newBlock = new Block(blockchain.get(blockchain.size() - 1).getHash());
//            newBlock.addTransaction(transaction);
//            blockchain.add(newBlock);
//        }
//    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = in.readLine()) != null) {
//                System.err.println("Received: " + line);
                System.out.println("some message was sent");
//                Block newBlock = new Block(blockchain.getLast().getHash());
//                newBlock.addTransaction();    // here need to add transaction in order to complete
//                blockchain.add(newBlock);
                String forEveryone = line;
                clients.stream().filter(client -> client != this).forEach(client -> client.send(forEveryone));
            }
        } catch (Exception e) {
            System.out.println("Protocol violation! " + e.getMessage());
        }
    }

    public void send(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (Exception e) {
            System.out.println("Socket broke");
        }
    }
}
