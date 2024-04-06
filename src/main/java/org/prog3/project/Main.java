package org.prog3.project;

import org.prog3.project.Network.NetworkManager;

public class Main {
    public static void main(String[] args) {
        System.err.println("Hello World!");
        NetworkManager networkManager = new NetworkManager();
        networkManager.connect("localhost", 12137);
    }
}
