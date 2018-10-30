package server.messageHandlers;

import server.IJavaCommunicatorServer;
import shared.messages.HandShakeMessage;
import shared.IHandle;

public class HandshakeMessageHandler implements IHandle<HandShakeMessage>
{
    private IJavaCommunicatorServer _server;

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
