package shared.messages;

public class TextMessage extends MessageBase
{
    private final int recipientId;
    private final String message;

    public TextMessage(int recipientId, String message)
    {
        this.recipientId = recipientId;
        this.message = message;
    }
}
