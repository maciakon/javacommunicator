package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import shared.HandShakeMessage;
import shared.IHandle;
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
    private IHandle handler;

    public ClientController()
    {
        _javaCommunicatorClient = new JavaCommunicatorClient(_host, _portNumber);
        _javaCommunicatorClient.Start();
        _readingThread = new Thread(this::ShowReceivedMessages);
        _readingThread.start();
        SendHandshake();
    }

    private void ShowReceivedMessages()
    {
        while(!_readingThread.isInterrupted())
        {
            var packet = _javaCommunicatorClient.Receive();
            var handlerFactory = new HandlerFactory(contactsList);
            handlerFactory.Get(packet).Handle(packet.get_message());

            handler.Handle(packet.get_message());

            if (packet != null)
            {

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

    private void SendHandshake()
    {
        var handShakeMessage = new HandShakeMessage();
        handShakeMessage.Name = "TestName";
        var packet = new Packet(0, 0, handShakeMessage);
        _javaCommunicatorClient.Send(packet);
    }
}
