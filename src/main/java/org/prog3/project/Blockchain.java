package org.prog3.project;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Blockchain {
    private List<Block> blockchain;
    public Blockchain() {
        this.blockchain = new ArrayList<>();
    }
    public void addBlock(Block block) {
        this.blockchain.add(block);
    }
    public int size() {
        return this.blockchain.size();
    }
}
