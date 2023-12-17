package org.prog3.project;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PikkwolfChainTest {
    @Test
    public void givenBlockchain_whenNewBlockAdded_thenSuccess() {
        Block newBlock = new Block(
                "The is a New Block.",
                PikkwolfChain.blockchain.get(PikkwolfChain.blockchain.size() - 1).getHash()
        );
        newBlock.mineBlock(newBlock.getNonce());
        assertTrue(false);
        PikkwolfChain.blockchain.add(newBlock);
    }

}