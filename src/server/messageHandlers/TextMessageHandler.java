package server.messageHandlers;

import server.IJavaCommunicatorServer;
import shared.IHandle;
import shared.messages.TextMessage;

public class TextMessageHandler implements IHandle<TextMessage>
{
    private IJavaCommunicatorServer javaCommunicatorServer;

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
