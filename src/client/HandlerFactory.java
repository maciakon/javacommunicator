package client;

import javafx.scene.control.ListView;
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
        _handlers.put(ContactsListUpdatedHandler.class, new ContactsListUpdatedHandler(_contactsList));
    }

    @Override
    public IHandle Get(Packet packet)
    {
        return _handlers.get(packet.getClass());
    }
}
