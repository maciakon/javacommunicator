package server.implementation.messageHandlers;

import server.interfaces.IJavaCommunicatorServer;
import shared.interfaces.IHandle;
import shared.implementation.messages.TextMessage;

public class TextMessageHandler implements IHandle<TextMessage>
{
    private final IJavaCommunicatorServer javaCommunicatorServer;

    public TextMessageHandler(IJavaCommunicatorServer javaCommunicatorServer)
    {
        this.javaCommunicatorServer = javaCommunicatorServer;
    }

    @Override
    public void Handle(TextMessage message)
    {
        this.javaCommunicatorServer
                .get_connectedClients()
                .stream()
                .filter(client -> client.get_Id() == message.getRecipientId())
                .forEach(client -> client.Send(message));
    }
}
