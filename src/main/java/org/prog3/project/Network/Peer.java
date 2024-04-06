package org.prog3.project.Network;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.*;
import java.net.Socket;

public class Peer implements Runnable {
    Socket connection;
    BufferedReader in;
    BufferedWriter out;
    NetworkManager networkManager;
    public Peer(NetworkManager networkManager, Socket connection) {
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
        String msg;
        try{
            while((msg = in.readLine()) != null) {
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
}
