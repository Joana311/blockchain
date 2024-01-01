package org.prog3.project;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Transaction.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

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
        return StringUtil.applySha256(prevHash + timeStamp + nonce + merkleRoot);
    }

    public void mineBlock(int difficulty) { //Increases nonce value until hash target is reached.
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }

    public void addTransaction(Transaction transaction) {   //Add transactions to block
        if (transaction == null) return;    // process transaction and check if valid, unless block is genesis then ignore
        if ((!Objects.equals(prevHash, "0"))) {
            if ((!transaction.processTransaction())) {
                System.out.println("Transaction failed to process. Discarded.");
                return;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
    }
}
