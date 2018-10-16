package shared;

import java.io.Serializable;

public class Message implements Serializable
{
    int recipientId;
    int senderId;
    private String _message;

    public String get_message()
    {
        return _message;
    }
}
