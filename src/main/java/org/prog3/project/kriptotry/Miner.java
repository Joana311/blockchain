package org.prog3.project.kriptotry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Miner {
    private double reward;

    public void mine(Block block, Blockchain blockchain) {
        String target = new String(new char[Constants.DIFFICULTY]).replace('\0', '0');
        while (!block.getHash().substring(0, Constants.DIFFICULTY).equals(target)) {
            block.incrementNonce();
            block.setHash(block.generateHash());
        }
        System.out.println("Block Mined! : " + block.getHash());
        blockchain.addBlock(block);
        reward += Constants.MINER_REWARD;
    }


}
