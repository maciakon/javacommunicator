package client.mainWindow.messageHandlers;

import client.mainWindow.ClientController;
import client.mainWindow.JavaCommunicatorClient;
import javafx.scene.control.ListView;
import shared.interfaces.IHandlerFactory;
import shared.implementation.messages.ContactsListUpdatedMessage;
import shared.interfaces.IHandle;
import shared.interfaces.messages.IMessage;
import shared.implementation.messages.TextMessage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for creating client message handlers.
 * All of the message handlers have to be registered here.
 */
public class ClientHandlerFactory implements IHandlerFactory
{
    private final Map<Type, IHandle> _handlers = new HashMap<>();
    private final ListView _contactsList;
    private final ClientController _clientController;
    private final JavaCommunicatorClient _client;

    /**
     * Creates the factory,
     * @param contactsList current contacts list
     * @param clientController client controller
     * @param client client engine
     */
    public ClientHandlerFactory(ListView contactsList, ClientController clientController, JavaCommunicatorClient client)
    {
        _contactsList = contactsList;
        _clientController = clientController;
        _client = client;
        RegisterHandlers();
    }

    private void RegisterHandlers()
    {
        _handlers.put(ContactsListUpdatedMessage.class, new ContactsListUpdatedHandler(_contactsList, _client));
        _handlers.put(TextMessage.class, new TextMessageHandler(_clientController, _client));
    }

    /**
     * Returns appropriate message handler for a message type.
     * @param message {@link IMessage} instance
     * @return a message handler
     */
    @Override
    public IHandle Get(IMessage message)
    {
        return _handlers.get(message.getClass());
    }
}
