package server.implementation.messageHandlers;

import server.interfaces.IJavaCommunicatorServer;
import shared.implementation.messages.HandShakeMessage;
import shared.interfaces.IHandle;

public class HandshakeMessageHandler implements IHandle<HandShakeMessage>
{
    private final IJavaCommunicatorServer _server;

    HandshakeMessageHandler(IJavaCommunicatorServer server)
    {
        _server = server;
    }

    @Override
    public void Handle(HandShakeMessage message)
    {
        _server.AddClientId(message.GetSender(), message.Name);
    }
}
