package org.prog3.project;

import com.google.gson.GsonBuilder;

public class ApshyMAINcoin {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        Miner miner = new Miner();

        Block block = new Block(0, "transaction0", Constants.GENESIS_PREV_HASH);
        miner.mine(block, blockchain);

        Block block1 = new Block(1, "transaction1", blockchain.getBlockchain().getLast().getHash());
        miner.mine(block1, blockchain);

        Block block2 = new Block(2, "transaction2", blockchain.getBlockchain().getLast().getHash());
        miner.mine(block2, blockchain);


        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
        System.err.println(miner.getReward());
    }
}
