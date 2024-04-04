package lecture8SCKET;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        System.err.println("helo!");
        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Client> blockchain = new ArrayList<>();

        try {
            ServerSocket server = new ServerSocket(12137);
            while (true) {
                Socket connection = server.accept();
                Client newClient = new Client(connection, clients);
                clients.add(newClient);
                executorService.submit(newClient);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
