package shared.messages;

import java.util.HashMap;

public class ContactsListUpdatedMessage extends MessageBase
{
    private HashMap<Integer, String> contacts;

    public ContactsListUpdatedMessage(HashMap<Integer, String> contacts)
    {
        this.contacts = contacts;
    }

    public HashMap<Integer, String> getContacts()
    {
        return contacts;
    }

}
