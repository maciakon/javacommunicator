package client.mainWindow.messageHandlers;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import shared.messages.ContactsListUpdatedMessage;
import shared.IHandle;

public class ContactsListUpdatedHandler implements IHandle<ContactsListUpdatedMessage>
{
    private ListView _contactsList;

    public ContactsListUpdatedHandler(ListView contactsList)
    {
        _contactsList = contactsList;
    }

    @Override
    public void Handle(ContactsListUpdatedMessage message)
    {
        _contactsList.setItems(FXCollections.observableArrayList(message.getContacts().values()));
    }
}