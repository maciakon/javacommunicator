package shared;

import java.io.Serializable;

public class Packet<T> implements Serializable
{
    private int _recipientId;
    private int _senderId;
    private T _message;

    public Packet(int recipientId, int senderId, T message)
    {
        _recipientId = recipientId;
        _senderId = senderId;
        _message = message;
    }

    public T get_message()
    {
        return _message;
    }
    public int get_recipientId(){ return _recipientId;}
    public int get_senderId(){ return _senderId;}
}
