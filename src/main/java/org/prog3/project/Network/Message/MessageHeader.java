package org.prog3.project.Network.Message;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.s.Protocol;

@Getter
@Setter
public class MessageHeader {
    private long timestamp;

    private String privateKey;
    private String publicKey;
    private String signature;
    private Protocol protocol;

    public MessageHeader(long timestamp, String signature) {
        this.timestamp = timestamp;
        this.signature = signature;
    }



}
