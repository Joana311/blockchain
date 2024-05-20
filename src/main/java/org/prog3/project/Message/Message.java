package org.prog3.project.Message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    MessageHeader header;
    String body;

    public Message(MessageHeader header, String body) {
        this.header = header;
        this.body = body;
    }
}
