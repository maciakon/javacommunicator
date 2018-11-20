package shared.implementation.messages;

import java.util.HashMap;

public class ContactsListUpdatedMessage extends MessageBase
{
    private final HashMap<Integer, String> contacts;

    public ContactsListUpdatedMessage(HashMap<Integer, String> contacts)
    {
        this.contacts = contacts;
    }

    public HashMap<Integer, String> getContacts()
    {
        return contacts;
    }

}
