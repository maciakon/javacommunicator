package shared.implementation.messages;

import shared.interfaces.messages.IMessage;

import java.io.Serializable;

public class MessageBase implements Serializable, IMessage
{
    private int port;
    private int recipient;

    @Override
    public final void SetSender(int port)
    {
        this.port = port;
    }

    @Override
    public final int GetSender()
    {
        return port;
    }

    public int getRecipientId()
    {
        return recipient;
    }

    void setRecipient(int recipientId)
    {
        this.recipient = recipientId;
    }
}
