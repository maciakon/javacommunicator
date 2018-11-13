package client.mainWindow.messageHandlers;

import client.mainWindow.ClientController;
import client.mainWindow.JavaCommunicatorClient;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import shared.messages.ContactsListUpdatedMessage;
import shared.IHandle;
import shared.messages.IMessage;
import shared.messages.TextMessage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ClientHandlerFactory implements shared.IHandlerFactory
{
    Map<Type, IHandle> _handlers = new HashMap<>();
    private ListView _contactsList;
    private TabPane _conversationTabPane;
    private ClientController _clientController;
    private JavaCommunicatorClient _client;

    public ClientHandlerFactory(ListView contactsList, TabPane conversationTabPane, ClientController clientController, JavaCommunicatorClient client)
    {
        _contactsList = contactsList;
        _conversationTabPane = conversationTabPane;
        _clientController = clientController;
        _client = client;
        RegisterHandlers();
    }

    private void RegisterHandlers()
    {
        _handlers.put(ContactsListUpdatedMessage.class, new ContactsListUpdatedHandler(_contactsList, _client));
        _handlers.put(TextMessage.class, new TextMessageHandler(_clientController, _client));
    }

    @Override
    public IHandle Get(IMessage message)
    {
        return _handlers.get(message.getClass());
    }
}
