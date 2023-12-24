package org.prog3.project;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Block {
    public String hash;
    public String prevHash;
    private final String datA;
    private final long timeStamp;
    private int nonce;  // the difficulty?

    public Block(String data, String prevHash) {
        this.datA = data;
        this.prevHash = prevHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(
                prevHash +
                        timeStamp +
                        nonce +
                        datA);
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined! : " + hash);
    }
}
