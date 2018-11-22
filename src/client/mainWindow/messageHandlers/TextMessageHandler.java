package client.mainWindow.messageHandlers;

import client.mainWindow.ClientController;
import client.mainWindow.JavaCommunicatorClient;
import javafx.application.Platform;
import shared.interfaces.IHandle;
import shared.implementation.messages.TextMessage;

/**
 * Handles text message,
 */
public class TextMessageHandler implements IHandle<TextMessage>
{
    private final ClientController _clientController;
    private final JavaCommunicatorClient _client;

    /**
     * Creates instance of this class,
     * @param clientController client controller
     * @param client engine
     */
    public TextMessageHandler(ClientController clientController, JavaCommunicatorClient client)
    {
        _clientController = clientController;
        _client = client;
    }

    /**
     * Finds appropriate tab controller to add a message to the correct conversation.
     * If no tab is opened, it opens one,
     * @param message a message to be handled.
     */
    @Override
    public void Handle(TextMessage message)
    {
        Platform.runLater(()->
        {
            // find appropriate conversation tab controller
            var tabController = _client.getConversationTabsControllers().get(message.GetSender());
            if(tabController == null)
            {
                // no conversation found, try add one
                _client.getContacts().forEach((senderContactIndex, value) ->
                {
                    if (value.getKey() == message.GetSender())
                    {
                        _clientController.AddTab(senderContactIndex);
                    }
                });

                tabController = _client.getConversationTabsControllers().get(message.GetSender());
            }
            tabController.AppendMessage(message, "");
        });
    }
}
