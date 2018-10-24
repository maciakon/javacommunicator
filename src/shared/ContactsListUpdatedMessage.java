package shared;

import java.io.Serializable;
import java.util.Map;

public class ContactsListUpdatedMessage implements Serializable
{
    private Map<Integer, String> contacts;

    public ContactsListUpdatedMessage(Map<Integer, String> contacts)
    {
        this.contacts = contacts;
    }

    public Map<Integer, String> getContacts()
    {
        return contacts;
    }
}
