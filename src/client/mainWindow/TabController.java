package client.mainWindow;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

public class TabController
{
    private final JavaCommunicatorClient client;
    private final String tabName;

    @FXML
    Tab conversationTab;
    @FXML
    TextField sendMessageTextField;

    public TabController(JavaCommunicatorClient client, String tabName)
    {
        this.client = client;
        this.tabName = tabName;
    }

    public void SendMessageAction(ActionEvent actionEvent)
    {
        var messageToSend = sendMessageTextField.getText();
    }

    @FXML
    protected void initialize()
    {
        Platform.runLater(() -> sendMessageTextField.requestFocus());
        conversationTab.setText(this.tabName);
    }
}