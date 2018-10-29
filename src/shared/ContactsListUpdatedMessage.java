package shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ContactsListUpdatedMessage implements Serializable
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
