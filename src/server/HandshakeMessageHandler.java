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
    public void Handle(int senderId, int receiverId, HandShakeMessage handshake)
    {
        _server.AddClientId(senderId, handshake.Name);
    }
}
