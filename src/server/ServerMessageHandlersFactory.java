package server;

import shared.HandShakeMessage;
import shared.Packet;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ServerMessageHandlersFactory
{
    Map<Type, IServerMessageHandler> _handlers = new HashMap<>();
    private JavaCommunicatorServer _javaCommunicatorServer;

    public ServerMessageHandlersFactory(JavaCommunicatorServer javaCommunicatorServer)
    {
        _javaCommunicatorServer = javaCommunicatorServer;
        RegisterHandlers();
    }

    private void RegisterHandlers()
    {
        _handlers.put(HandShakeMessage.class, new HandshakeMessageHandler(_javaCommunicatorServer));
    }

    public IServerMessageHandler Get(Packet packet)
    {
        return _handlers.get(packet.get_message().getClass());
    }
}
