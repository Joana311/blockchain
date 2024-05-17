package org.prog3.project.Network;

import java.io.*;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.PriorityQueue;
import java.util.Queue;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Message.Message;
import org.prog3.project.Message.MessageHeader;
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
        String msg;
        try {
            while ((msg = in.readLine()) != null) {
                System.out.println("Msg: " + msg);
                // TODO: MAKE THE PROTOCOL WORK CURRENTLY IT IS USED TO AVOID ERROR
                MessageHeader header = new MessageHeader(protocol);
                header.setPublicKey(networkManager.getCr().getKeyPair().getPublic());
                header.setSignature(networkManager.getCr().applySHA256(msg +
                        header.getTimestamp() +
                        header.getProtocol() +
                        header.getPublicKey()));
                Message message = new Message(header, msg);
                // TODO: if the message is valid (verified), add it to queue for broadcasting
                networkManager.getQueue().add(message);
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
