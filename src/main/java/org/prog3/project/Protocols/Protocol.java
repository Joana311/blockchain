package org.prog3.project.Protocols;

import org.prog3.project.Message.Message;
import org.prog3.project.Network.Peer;

public interface Protocol {
    void digest(Message message);
    void reply(Message message, Peer peer);
}
