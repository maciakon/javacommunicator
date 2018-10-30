package server;

import shared.TextMessage;

public class TextMessageHandler implements IServerMessageHandler<TextMessage>
{
    private IJavaCommunicatorServer javaCommunicatorServer;

    public TextMessageHandler(IJavaCommunicatorServer javaCommunicatorServer)
    {
        this.javaCommunicatorServer = javaCommunicatorServer;
    }

    @Override
    public void Handle(int senderId, int receiverId, TextMessage message)
    {

    }
}
