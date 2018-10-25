package client.mainWindow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import shared.Packet;

public class ClientController
{
    private final JavaCommunicatorClient _javaCommunicatorClient;
    private final Thread _readingThread;

    @FXML
    ListView contactsList;
    @FXML
    TextArea messageList;

    int _portNumber = 4441;
    String _host = "localhost";

    public ClientController()
    {
        _javaCommunicatorClient = new JavaCommunicatorClient(_host, _portNumber);
        _javaCommunicatorClient.Start();
        _readingThread = new Thread(this::ShowReceivedMessages);
        _readingThread.start();
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
                _readingThread.sleep(200);
            } catch (InterruptedException e)
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


}
