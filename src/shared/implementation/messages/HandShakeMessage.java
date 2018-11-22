package shared.implementation.messages;

/**
 * This message is sent to the server after a client established a connection.
 * Carries information about the name of the connected client.
 * This information is needed for {@link ContactsListUpdatedMessage}.
 */
public class HandShakeMessage extends MessageBase
{
    /**
     * A newly connected client name (sender).
     */
    public String Name;
}
