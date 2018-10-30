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
    public void Handle(TextMessage type)
    {

    }
}
