package client.mainWindow.messageHandlers;

import client.mainWindow.JavaCommunicatorClient;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import shared.IHandle;
import shared.messages.ContactsListUpdatedMessage;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ContactsListUpdatedHandler implements IHandle<ContactsListUpdatedMessage>
{
    private ListView _contactsList;
    private JavaCommunicatorClient _client;

    public ContactsListUpdatedHandler(ListView contactsList, JavaCommunicatorClient client)
    {
        _contactsList = contactsList;
        _client = client;
    }

    @Override
    public void Handle(ContactsListUpdatedMessage message)
    {
        var listToDisplay = FXCollections.observableArrayList();
        HashMap<Integer, Map.Entry<Integer, String>> indexOnDisplayedListToClients = new HashMap<>();
        AtomicInteger index = new AtomicInteger();
        message.getContacts().forEach((Integer port, String name) ->
        {
            listToDisplay.add(name);
            Map.Entry<Integer, String> singleEntry = new AbstractMap.SimpleEntry(port, name);
            indexOnDisplayedListToClients.put(index.get(), singleEntry);
            index.addAndGet(1);
        });
        _contactsList.setItems(listToDisplay);
        _client.setContacts(indexOnDisplayedListToClients);
    }
}
