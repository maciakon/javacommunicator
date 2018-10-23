package server;

import shared.HandShakeMessage;

public class HandshakeMessageHandler implements IServerMessageHandler<HandShakeMessage>
{
    private IJavaCommunicatorServer _server;

    public HandshakeMessageHandler(IJavaCommunicatorServer server)
    {
        _server = server;
    }

    @Override
    public void Handle(int clientId, HandShakeMessage handshake)
    {
        _server.AddClientId(clientId, handshake.Name);
    }
}
