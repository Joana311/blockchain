package org.prog3.project.Message;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Protocols.BlockchainProtocol;

import java.security.PublicKey;

import static java.lang.System.currentTimeMillis;

@Getter
@Setter
public class MessageHeader {
    private long timestamp;
    private BlockchainProtocol protocol;
    private PublicKey publicKey;
    private String signature;

    public MessageHeader(BlockchainProtocol protocol) {
        this.timestamp = currentTimeMillis();
        this.protocol = protocol;
    }

}

