package client.mainWindow;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import shared.messages.TextMessage;

public class TabController
{
    private final JavaCommunicatorClient client;
    private int _contactIndex;

    @FXML
    Tab conversationTab;
    @FXML
    TextField sendMessageTextField;

    public TabController(JavaCommunicatorClient client, int contactIndex)
    {
        this.client = client;
        this._contactIndex = contactIndex;
    }

    public void SendMessageAction(ActionEvent actionEvent)
    {
        var textToSend = sendMessageTextField.getText();
        var clientId = this.client.getContacts().get(_contactIndex).getKey();
        var messageToSend = new TextMessage(clientId, textToSend);
        this.client.Send(messageToSend);
    }

    @FXML
    protected void initialize()
    {
        Platform.runLater(() -> sendMessageTextField.requestFocus());
        var clientName = this.client.getContacts().get(_contactIndex).getValue();
        conversationTab.setText(clientName);
    }
}