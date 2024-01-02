package org.prog3.project;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {
    Socket connection;
    BufferedReader in;
    BufferedWriter out;
    private final ArrayList<Block> blockchain;
    ArrayList<Client> clients;

    public Client(Socket connection, ArrayList<Client> clients, ArrayList<Block> blockchain) throws IOException {
        this.connection = connection;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        String line;
        try {
            Wallet wallet = new Wallet();
            Block block1 = new Block(blockchain.getLast().hash);
            // TODO: make wallet, ask somehow where to send money, and create Transaction
//            block1.addTransaction(wallet.sendFunds(wallet.publicKey, 10f));
            block1.addTransaction(PikkwolfChain.walletA.sendFunds(wallet.publicKey, 10f));
            PikkwolfChain.addBlock(block1);
            PikkwolfChain.isChainValid();

            while ((line = in.readLine()) != null) {
                System.err.println("Recieved: " + line);
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
