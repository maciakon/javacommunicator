package server.implementation.messageHandlers;

import server.implementation.JavaCommunicatorServer;
import shared.interfaces.IHandle;
import shared.interfaces.IHandlerFactory;
import shared.implementation.messages.HandShakeMessage;
import shared.interfaces.messages.IMessage;
import shared.implementation.messages.TextMessage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ServerMessageHandlersFactory implements IHandlerFactory
{
    private final Map<Type, IHandle> _handlers = new HashMap<>();
    private final JavaCommunicatorServer _javaCommunicatorServer;

    public ServerMessageHandlersFactory(JavaCommunicatorServer javaCommunicatorServer)
    {
        _javaCommunicatorServer = javaCommunicatorServer;
        RegisterHandlers();
    }

    private void RegisterHandlers()
    {
        _handlers.put(HandShakeMessage.class, new HandshakeMessageHandler(_javaCommunicatorServer));
        _handlers.put(TextMessage.class, new TextMessageHandler(_javaCommunicatorServer));
    }

    public IHandle Get(IMessage message)
    {
        return _handlers.get(message.getClass());
    }
}
