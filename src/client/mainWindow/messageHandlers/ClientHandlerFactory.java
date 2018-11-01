package client.mainWindow.messageHandlers;

import javafx.scene.control.ListView;
import shared.messages.ContactsListUpdatedMessage;
import shared.IHandle;
import shared.messages.IMessage;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ClientHandlerFactory implements shared.IHandlerFactory
{
    Map<Type, IHandle> _handlers = new HashMap<>();
    private ListView _contactsList;

    public ClientHandlerFactory(ListView contactsList)
    {
        _contactsList = contactsList;
        RegisterHandlers();
    }

    private void RegisterHandlers()
    {
        _handlers.put(ContactsListUpdatedMessage.class, new ContactsListUpdatedHandler(_contactsList));
    }

    @Override
    public IHandle Get(IMessage message)
    {
        return _handlers.get(message.getClass());
    }
}
