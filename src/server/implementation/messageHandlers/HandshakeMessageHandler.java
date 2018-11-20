package server.implementation.messageHandlers;

import server.interfaces.IJavaCommunicatorServer;
import shared.implementation.messages.HandShakeMessage;
import shared.interfaces.IHandle;

/**
 * Handles {@link HandShakeMessage} on the server side.
 * That means adding the handshaking client to the contacts list and sending updated list to the rest of the clients.
 */
public class HandshakeMessageHandler implements IHandle<HandShakeMessage>
{
    private final IJavaCommunicatorServer _server;

    HandshakeMessageHandler(IJavaCommunicatorServer server)
    {
        _server = server;
    }

    /**
     * Schedules handling to the server.
     * Server adds the client to the list and informs other clients.
     * @param message
     */
    @Override
    public void Handle(HandShakeMessage message)
    {
        _server.AddClient(message.GetSender(), message.Name);
    }
}
