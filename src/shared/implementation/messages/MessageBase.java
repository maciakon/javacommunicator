package shared.implementation.messages;

import shared.interfaces.messages.IMessage;

import java.io.Serializable;

/**
 * Contains logic common for all of the messages.
 */
public class MessageBase implements Serializable, IMessage
{
    private int port;
    private int recipient;

    /**
     * See interface definition for details
     * @param port Client connection socket number.
     */
    @Override
    public final void SetSender(int port)
    {
        this.port = port;
    }

    /**
     * See interface {@link IMessage} definition for details.
     * @return Sender's port address.
     */
    @Override
    public final int GetSender()
    {
        return port;
    }

    /**
     * Gets recipient address as a socket number.
     * @return recipient's socket number.
     */
    public int getRecipientId()
    {
        return recipient;
    }

    /**
     * Sets recipient adress as a socket number.
     * @param recipientId recipient socket number,
     */
    void setRecipient(int recipientId)
    {
        this.recipient = recipientId;
    }
}
