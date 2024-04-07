package org.prog3.project.protocols;

import org.prog3.project.Network.Message.Message;

public interface Protocol {
    public void digest (Message message);
}
