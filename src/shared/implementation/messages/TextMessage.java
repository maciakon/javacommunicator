package shared.implementation.messages;

public class TextMessage extends MessageBase
{
    private final String message;

    public TextMessage(int recipientId, String message)
    {
        setRecipient(recipientId);
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
