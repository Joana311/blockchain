package org.prog3.project.Message;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Protocols.Protocol;

import static java.lang.System.currentTimeMillis;

@Getter
@Setter
public class MessageHeader {
    private long timestamp;

    private String privateKey;
    private String publicKey;
    private String signature;
    private Protocol protocol;

    // TODO: edit the constructor as it should, different constructors, set the public and private keys
    public MessageHeader(String privateKey, String publicKey, String signature, Protocol protocol) {
        this.timestamp = currentTimeMillis();
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.signature = signature;
        this.protocol = protocol;
    }
}
