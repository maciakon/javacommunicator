package client.mainWindow;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import shared.messages.TextMessage;

public class TabController
{
    private final JavaCommunicatorClient _client;
    private int _contactIndex;

    @FXML
    Tab conversationTab;
    @FXML
    TextField sendMessageTextField;
    @FXML
    TextArea messagesArea;
    private String _contactName;
    private Integer _contactId;

    public TabController(JavaCommunicatorClient client, int contactIndex)
    {
        _client = client;
        _contactIndex = contactIndex;
    }

    public void SendMessageAction(ActionEvent actionEvent)
    {
        var textToSend = sendMessageTextField.getText();
        var clientId = _client.getContacts().get(_contactIndex).getKey();
        var messageToSend = new TextMessage(clientId, textToSend);
        _client.Send(messageToSend);
    }

    public void AppendMessage(TextMessage message)
    {
        messagesArea.appendText(message.GetSender() + ":" + message.getMessage()+"\n");
    }

    public void OnClosing(Event event)
    {
        _client.getConversationTabsControllers().remove(_contactId);
    }

    @FXML
    protected void initialize()
    {
        Platform.runLater(() -> sendMessageTextField.requestFocus());
        _contactName = _client.getContacts().get(_contactIndex).getValue();
        _contactId = _client.getContacts().get(_contactIndex).getKey();
        _client.getConversationTabsControllers().put(_contactId, this);
        conversationTab.setText(_contactName);
    }
}