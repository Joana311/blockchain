package org.prog3.project.Network;

import java.io.*;
import java.net.Socket;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Message.Message;
import org.prog3.project.Message.MessageHeader;
import org.prog3.project.Protocols.BlockchainProtocol;

@Getter
@Setter
public class Peer implements Runnable {
    private Socket connection;
    private BufferedReader in;
    private BufferedWriter out;
    private NetworkManager networkManager;
    private BlockchainProtocol protocol;

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
                MessageHeader header = new MessageHeader(protocol, this);
                header.setPublicKey(networkManager.getCr().getKeyPair().getPublic());
                header.setSignature(networkManager.getCr().applySHA256(msg + header.getTimestamp() + header.getProtocol() + header.getPublicKey()));
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
            this.in.close();
            this.out.close();
            this.connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
