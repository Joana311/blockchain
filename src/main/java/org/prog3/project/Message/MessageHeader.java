package org.prog3.project.Message;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Protocols.Protocol;
import org.prog3.project.Security.Crypto;

import java.security.PublicKey;

import static java.lang.System.currentTimeMillis;

@Getter
@Setter
public class MessageHeader {
    private long timestamp;
    private Protocol protocol;
    private PublicKey publicKey;
    private String signature;

    public MessageHeader(Protocol protocol) {
        this.timestamp = currentTimeMillis();
        this.protocol = protocol;
    }

}

