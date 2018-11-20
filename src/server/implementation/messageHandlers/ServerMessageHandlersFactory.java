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

/**
 * Encapsulates process of providing appropriate message handlers for specific message types.
 */
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

    /**
     * Returns a handler for specific message type.
     * The handler must have been previously registered to be correctly returned.
     * @param message A message to provide handler for.
     * @return Instance of the message handler.
     */
    public IHandle Get(IMessage message)
    {
        return _handlers.get(message.getClass());
    }
}
