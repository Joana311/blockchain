package org.prog3.project.Message;

import lombok.Getter;
import lombok.Setter;
import org.prog3.project.Protocols.Protocol;

@Getter
@Setter
public class Message {
    MessageHeader header;
    String body;

    // TODO: somehow need to add the private/public key or whatever
    public Message(String body, Protocol protocol) {
        this.body = body;
        this.header = new MessageHeader("", "", "", protocol);
    }

    public Message(MessageHeader header, String body) {
        this.header = header;
        this.body = body;
    }
}
