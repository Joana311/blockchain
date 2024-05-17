package org.prog3.project.Network;

import java.io.*;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Message.Message;
import org.prog3.project.Protocols.Protocol;


@Getter
@Setter
public class Peer implements Runnable {
    private Socket connection;
    private BufferedReader in;
    private BufferedWriter out;
    private NetworkManager networkManager;
    private Protocol protocol;

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
                // TODO: fix the protocol, probably need to pass it as value from/to somewhere
                Message message = new Message(msg, protocol);
                // TODO: if the message is valid (verified) broadcast it, otherwise error
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

    private void generateKeys() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
//            this.privateKey = pair.getPrivate();
//            this.publicKey = pair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("RSA algorithm not found", e);
        }
    }
}
