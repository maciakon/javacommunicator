package shared;

import java.io.Serializable;

public class Message implements Serializable
{
    private int _recipientId;
    private int _senderId;
    private String _message;

    public Message(int recipientId, int senderId, String message)
    {
        _recipientId = recipientId;
        _senderId = senderId;
        _message = message;
    }

    public String get_message()
    {
        return _message;
    }
}
