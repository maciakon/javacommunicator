package server.messageHandlers;

import server.JavaCommunicatorServer;
import shared.*;
import shared.messages.HandShakeMessage;
import shared.messages.IMessage;
import shared.messages.TextMessage;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ServerMessageHandlersFactory implements IHandlerFactory
{
    private Map<Type, IHandle> _handlers = new HashMap<>();
    private JavaCommunicatorServer _javaCommunicatorServer;

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
