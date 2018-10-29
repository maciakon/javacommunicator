package client.mainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

    public void SendMessageClicked(ActionEvent actionEvent)
    {
        var message = new Packet(0, 0, "hello");
        _javaCommunicatorClient.Send(message);
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
                conversationTabPane.getTabs().add(tab);
            }
        }
    }
}
