package client.mainWindow.messageHandlers;

import client.mainWindow.ClientController;
import client.mainWindow.JavaCommunicatorClient;
import javafx.application.Platform;
import javafx.scene.control.TabPane;
import shared.IHandle;
import shared.messages.TextMessage;

import java.util.HashMap;
import java.util.Map;

public class TextMessageHandler implements IHandle<TextMessage>
{
    private ClientController _clientController;
    private JavaCommunicatorClient _client;

    public TextMessageHandler(ClientController clientController, JavaCommunicatorClient client)
    {
        _clientController = clientController;
        _client = client;
    }

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
