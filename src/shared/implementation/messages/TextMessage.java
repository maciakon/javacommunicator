package shared.implementation.messages;

/**
 * This is the message type that carries text information send between clients.
 */
public class TextMessage extends MessageBase
{
    private final String message;

    /**
     * Constructs TextMessage.
     * @param recipientId a recipient's port
     * @param message text to be send
     */
    public TextMessage(int recipientId, String message)
    {
        setRecipient(recipientId);
        this.message = message;
    }

    /**
     * Gets text from this message.
     * @return a message's text
     */
    public String getMessage()
    {
        return message;
    }
}
