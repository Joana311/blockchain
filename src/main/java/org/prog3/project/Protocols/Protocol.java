package org.prog3.project.Protocols;

import org.prog3.project.Message.Message;

public interface Protocol {
    enum Type {
        PING,
        PONG,
        NORMAL
    }
    public void digest (Message message);
}
