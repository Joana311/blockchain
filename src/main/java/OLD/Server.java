package OLD;

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
