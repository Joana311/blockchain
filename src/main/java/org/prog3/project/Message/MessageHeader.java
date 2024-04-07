package org.prog3.project.Message;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Protocols.Protocol;

@Getter
@Setter
public class MessageHeader {
    private long timestamp;

    private String privateKey;
    private String publicKey;
    private String signature;
    private Protocol protocol;

    public MessageHeader(String privateKey, String publicKey, String signature, Protocol protocol) {
        this.timestamp = System.currentTimeMillis();
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.signature = signature;
        this.protocol = protocol;
    }
}
