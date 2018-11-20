package server.implementation.messageHandlers;

import server.interfaces.IJavaCommunicatorServer;
import shared.interfaces.IHandle;
import shared.implementation.messages.TextMessage;

/**
 * Handles text messages send between clients.
 */
public class TextMessageHandler implements IHandle<TextMessage>
{
    private final IJavaCommunicatorServer javaCommunicatorServer;

    TextMessageHandler(IJavaCommunicatorServer javaCommunicatorServer)
    {
        this.javaCommunicatorServer = javaCommunicatorServer;
    }

    /**
     * Routes messages from a sender to a recipient.
     * The process is as follows:
     * <ul>
     *     <li>
     *         Get a recipient id from a message. It is client responsibility to set the recipient.
     *     </li>
     *     <li>
     *         Send (route) message to all of the server clients with the recipient id.
     *     </li>
     * </ul>
     * @param message
     */
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
