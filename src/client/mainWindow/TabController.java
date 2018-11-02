package client.mainWindow;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;


public class TabController {
    @FXML
    Tab conversationTab;
    @FXML
    TextField sendMessageTextField;

    public void setName(String contactName)
    {
        conversationTab.setText(contactName);
    }

    public void SendMessageAction(ActionEvent actionEvent)
    {
        var messageToSend = sendMessageTextField.getText();
    }

    @FXML
    protected void initialize()
    {
        Platform.runLater(() -> sendMessageTextField.requestFocus());
    }
}
