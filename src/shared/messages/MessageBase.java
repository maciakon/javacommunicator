package shared.messages;

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

    public void setRecipient(int recipientId)
    {
        this.recipient = recipientId;
    }
}
