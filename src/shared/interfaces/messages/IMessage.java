package shared.interfaces.messages;

/**
 * General interface definition to be implemented by all of the message classes.
 */
public interface IMessage
{
    /**
     * Set sender of the message. To be used on a server side.
     * @param port Client connection socket number.
     */
    void SetSender(int port);

    /**
     * Gets sender of a message.
     * @return Integer socket (port number).
     */
    int GetSender();
}
