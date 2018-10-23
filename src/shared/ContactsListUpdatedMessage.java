package shared;
import javafx.collections.ObservableList;

import java.io.Serializable;

public class ContactsListUpdatedMessage implements Serializable
{
    private ObservableList<String> contacts;

    public ContactsListUpdatedMessage(ObservableList<String> contacts)
    {
        this.contacts = contacts;
    }

    public ObservableList<String> getContacts()
    {
        return contacts;
    }
}
