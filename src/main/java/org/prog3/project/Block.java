package org.prog3.project;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class Block {
    public String hash;
    public String prevHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>(); //our data will be a simple message.
    private final long timeStamp;
    private int nonce;  // the difficulty?

    public Block(String prevHash) {
        this.prevHash = prevHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(
                prevHash +
                        timeStamp +
                        nonce +
                        merkleRoot);
    }

    //Increases nonce value until hash target is reached.
    public void mineBlock(int difficulty) {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    //Add transactions to this block
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((prevHash != "0")) {
            if((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
