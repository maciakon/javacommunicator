package client.mainWindow.messageHandlers;

import shared.IHandle;
import shared.messages.TextMessage;

public class TextMessageHandler implements IHandle<TextMessage>
{
    @Override
    public void Handle(TextMessage type)
    {
        // find appropriate conversation tab
        // in case there is not any, create and add to the tab container
        // get created tab text field
        // add it to the list of messages
    }
}
