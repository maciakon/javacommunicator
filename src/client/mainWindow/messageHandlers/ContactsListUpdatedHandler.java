package client.mainWindow.messageHandlers;

import client.mainWindow.JavaCommunicatorClient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import shared.interfaces.IHandle;
import shared.implementation.messages.ContactsListUpdatedMessage;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Handles {@link ContactsListUpdatedMessage}
 */
public class ContactsListUpdatedHandler implements IHandle<ContactsListUpdatedMessage>
{
    private final ListView _contactsList;
    private final JavaCommunicatorClient _client;

    /**
     * Creates the handler.
     * @param contactsList a contacts list
     * @param client client engine
     */
    public ContactsListUpdatedHandler(ListView contactsList, JavaCommunicatorClient client)
    {
        _contactsList = contactsList;
        _client = client;
    }

    /**
     * Gets a list of connected clients from the message.
     * Produces a map of a contact's index to port-name pair that is useful when assigning received messages to specific
     * contact's tab (because contacts may have the same name),
     * @param message a message to be handled.
     */
    @Override
    public void Handle(ContactsListUpdatedMessage message)
    {
        Platform.runLater(()->
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
        });

    }
}
