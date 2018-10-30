package client.mainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ClientController
{
    private JavaCommunicatorClient _javaCommunicatorClient;
    private Thread _readingThread;

    @FXML
    ListView contactsList;
    @FXML
    TabPane conversationTabPane;

    private int _portNumber = 4441;
    private String _host = "localhost";

    public void setLogin(String login)
    {
        _javaCommunicatorClient = new JavaCommunicatorClient(_host, _portNumber, login);
        _javaCommunicatorClient.Start();
        _readingThread = new Thread(this::ShowReceivedMessages);
        _readingThread.start();
    }

    public void ListViewMouseClicked(MouseEvent mouseEvent)
    {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
        {
            if (mouseEvent.getClickCount() == 2)
            {
                String item = (String)contactsList.getSelectionModel().getSelectedItem();
                var tab = new Tab();
                tab.setText(item);

                var messagesTextArea = new TextArea();
                messagesTextArea.setEditable(false);
                var messageToSendTextField = new TextField();

                var sendButton = new Button("Send");
                sendButton.setDefaultButton(true);
                sendButton.setOnAction(this::SendButtonClicked);

                var sendMessageHBox = new HBox();
                HBox.setHgrow(messageToSendTextField, Priority.ALWAYS);
                sendMessageHBox.getChildren().addAll(messageToSendTextField, sendButton);
                sendMessageHBox.setPadding(new Insets(10, 0, 0 ,0));
                sendMessageHBox.setSpacing(10);

                var borderPane = new BorderPane();
                borderPane.setCenter(messagesTextArea);
                borderPane.setBottom(sendMessageHBox);

                tab.setContent(borderPane);
                conversationTabPane.getTabs().add(tab);
                messageToSendTextField.requestFocus();
            }
        }
    }

    private void ShowReceivedMessages()
    {
        while(!_readingThread.isInterrupted())
        {
            var message = _javaCommunicatorClient.Receive();

            if (message != null)
            {
                var handlerFactory = new HandlerFactory(contactsList);
                var messageHandler = handlerFactory.Get(message);
                messageHandler.Handle(message);
            }
            try
            {
                Thread.sleep(200);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }



    private void SendButtonClicked(ActionEvent actionEvent)
    {

    }
}
