package client.mainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import shared.Packet;

public class ClientController
{
    private JavaCommunicatorClient _javaCommunicatorClient;
    private Thread _readingThread;

    @FXML
    ListView contactsList;
    @FXML
    TextArea messageList;
    @FXML
    TabPane conversationTabPane;

    private int _portNumber = 4441;
    private String _host = "localhost";

    public ClientController()
    {

    }

    private void ShowReceivedMessages()
    {
        while(!_readingThread.isInterrupted())
        {
            var packet = _javaCommunicatorClient.Receive();

            if (packet != null)
            {
                var handlerFactory = new HandlerFactory(contactsList);
                var messageHandler = handlerFactory.Get(packet);
                messageHandler.Handle(packet.get_message());
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
            if (mouseEvent.getClickCount() == 2) {
                String item = (String)contactsList.getSelectionModel().getSelectedItem();
                var tab = new Tab();
                tab.setText(item);

                var borderPane = new BorderPane();

                var messagesTextArea = new TextArea();
                messagesTextArea.setEditable(false);
                var messageToSendTextField = new TextField();
                var sendButton = new Button("Send");
                var sendMessageHBox = new HBox();
                HBox.setHgrow(messageToSendTextField, Priority.ALWAYS);
                sendMessageHBox.getChildren().addAll(messageToSendTextField, sendButton);
                sendMessageHBox.setPadding(new Insets(10, 0, 0 ,0));
                sendMessageHBox.setSpacing(10);

                borderPane.setCenter(messagesTextArea);
                borderPane.setBottom(sendMessageHBox);

                tab.setContent(borderPane);
                conversationTabPane.getTabs().add(tab);
            }
        }
    }
}
