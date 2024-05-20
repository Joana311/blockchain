package org.prog3.project.Network;

import java.io.*;
import java.net.Socket;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Message.Message;
import org.prog3.project.Protocols.BlockchainProtocol;

@Getter
@Setter
public class Peer implements Runnable {
    private Socket connection;
    private BufferedReader in;
    private BufferedWriter out;
    private NetworkManager networkManager;
    private BlockchainProtocol protocol;
    private Gson gson;

    public Peer(Socket connection, NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.connection = connection;
        this.gson = new Gson();
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
        try {
            while ((msg = in.readLine()) != null) {
                System.out.println("Msg: " + msg);
                Message message = gson.fromJson(msg, Message.class);
                // TODO: add the sender in some list/array of known peers?
                // TODO: if the message is valid (verified), add it to queue (need to be signed)
                if (networkManager.verifyMessage(message)) networkManager.getQueue().add(message);
                else disconnect();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message message) {
        try {
            out.write(gson.toJson(message));
            out.newLine();
            out.flush();
        } catch (IOException e) {
            System.err.println("Connection closed by remote host");
            throw new RuntimeException(e);
        }
    }

    public void disconnect() {
        try {
            this.in.close();
            this.out.close();
            this.connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
