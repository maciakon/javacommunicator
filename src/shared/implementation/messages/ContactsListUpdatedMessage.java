package shared.implementation.messages;

import java.util.HashMap;

/**
 * This message carries updated contact list to a client.
 * Usually send by a server every time a client connects or disconnects..
 */
public class ContactsListUpdatedMessage extends MessageBase
{
    private final HashMap<Integer, String> contacts;

    /**
     * Constructor.
     * @param contacts An updated contact list to be send to a client,
     */
    public ContactsListUpdatedMessage(HashMap<Integer, String> contacts)
    {
        this.contacts = contacts;
    }

    /**
     * Gets contact list from the message.
     * @return a dictionary of port-name pairs
     */
    public HashMap<Integer, String> getContacts()
    {
        return contacts;
    }

}
