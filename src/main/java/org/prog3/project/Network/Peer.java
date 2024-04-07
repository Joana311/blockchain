package org.prog3.project.Network;

import java.io.*;
import java.net.Socket;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Peer implements Runnable {
    private Socket connection;
    private BufferedReader in;
    private BufferedWriter out;
    private NetworkManager networkManager;

    public Peer(Socket connection, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.connection = connection;
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        // TODO: implement what actually is needed, the message-exchange system
        String msg;
        try {
            while ((msg = in.readLine()) != null) {
                System.out.println("Msg: " + msg);
                networkManager.broadcast(msg);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            System.err.println("Connection closed by remote host");
        }
    }

    public void disconnect() {
        try {
            in.close();
            out.close();
            connection.close();
        } catch (IOException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
