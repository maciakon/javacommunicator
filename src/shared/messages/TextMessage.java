package shared.messages;

public class TextMessage extends MessageBase
{
    private final String message;

    public TextMessage(int recipientId, String message)
    {
        setRecipient(recipientId);
        this.message = message;
    }
}
