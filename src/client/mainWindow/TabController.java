package client.mainWindow;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import shared.implementation.messages.TextMessage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TabController
{
    private final JavaCommunicatorClient _client;
    private final DateTimeFormatter _dateTimeFormatter;
    private final int _contactIndex;

    @FXML
    Tab conversationTab;
    @FXML
    TextField sendMessageTextField;
    @FXML
    ListView messagesArea;

    private String _contactName;
    private Integer _contactId;
    private final ObservableList _messagesList;

    public TabController(JavaCommunicatorClient client, int contactIndex)
    {
        _client = client;
        _contactIndex = contactIndex;
        _messagesList = FXCollections.observableArrayList();
        _dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    public void SendMessageAction(ActionEvent actionEvent)
    {
        var textToSend = sendMessageTextField.getText();
        if(!textToSend.isEmpty())
        {
            var clientId = _client.getContacts().get(_contactIndex).getKey();
            var messageToSend = new TextMessage(clientId, textToSend);
            _client.Send(messageToSend);
            sendMessageTextField.clear();
            AppendMessage(messageToSend, _client.GetLogin());
        }
    }

    public void AppendMessage(TextMessage message, String login)
    {
        var receivedDate = LocalDateTime.now().format(_dateTimeFormatter);
        var namePart = new Label();
        namePart.setText(login.isEmpty() ? _contactName : login);
        namePart.setAlignment(Pos.CENTER_LEFT);

        var datePart = new Label();
        datePart.setText("[" + receivedDate + "] ");
        datePart.setAlignment(Pos.CENTER_RIGHT);

        var messagePart = new Label();
        messagePart.setFont(Font.font(Font.getDefault().toString(),  FontWeight.BOLD, Font.getDefault().getSize()));
        messagePart.setText(message.getMessage());

        VBox b = new VBox();
        HBox nameAndDate = new HBox();
        nameAndDate.getChildren().addAll(datePart, namePart);
        b.getChildren().addAll(nameAndDate, messagePart);

        _messagesList.add(b);
        messagesArea.setItems(_messagesList);
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