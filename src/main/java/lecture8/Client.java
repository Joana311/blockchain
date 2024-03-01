package lecture8;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {
    Socket connection;
    BufferedReader in;
    BufferedWriter out;
    ArrayList<Client> clients;

    public Client(Socket connection, ArrayList<Client> clients) throws IOException {
        this.connection = connection;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
    }

    @Override
    public void run() {
        String line;
        try {
            while ((line = in.readLine()) != null) {
                System.err.println("Received: " + line);
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
