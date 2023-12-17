package org.prog3.project;

import java.util.ArrayList;

import com.google.gson.GsonBuilder;


public class AlowolChain {
    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 5;

    public static void main(String[] args) {

        blockchain.add(new Block("Hi, pik1", "0"));
        System.err.println("mining pik1");
        blockchain.getFirst().mineBlock(difficulty);


        blockchain.add(new Block("Yo, pik2", blockchain.get(blockchain.size() - 1).hash));
        System.err.println("mining pik2");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Hey, pik3", blockchain.get(blockchain.size() - 1).hash));
        System.err.println("mining pik3");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.err.println("The blockchain is as follows below:");
        System.out.println(blockchainJson);
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //loop through blockchain to check hashes:
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            //compare registered hash and calculated hash:
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if (!previousBlock.hash.equals(currentBlock.prevHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            //check if hash is solved
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

}
