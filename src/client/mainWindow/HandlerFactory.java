package client.mainWindow;

import javafx.scene.control.ListView;
import shared.ContactsListUpdatedMessage;
import shared.IHandle;
import shared.Packet;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class HandlerFactory implements shared.IHandlerFactory
{
    Map<Type, IHandle> _handlers = new HashMap<>();
    private ListView _contactsList;

    public HandlerFactory(ListView contactsList)
    {
        _contactsList = contactsList;
        RegisterHandlers();
    }

    private void RegisterHandlers()
    {
        _handlers.put(ContactsListUpdatedMessage.class, new ContactsListUpdatedHandler(_contactsList));
    }

    @Override
    public IHandle Get(Packet packet)
    {
        return _handlers.get(packet.get_message().getClass());
    }
}
