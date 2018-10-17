package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import shared.Message;

import java.awt.*;

public class ClientController
{
    private final JavaCommunicatorClient _javaCommunicatorClient;
    private final Thread _readingThread;
    @FXML
    TextArea messagesTextBox;

    int _portNumber = 4441;
    String _host = "localhost";

    public ClientController()
    {
        _javaCommunicatorClient = new JavaCommunicatorClient(_host, _portNumber);
        _javaCommunicatorClient.Start();
        _readingThread = new Thread(this::ShowReceivedMessages);
        //_readingThread.start();
    }

    private void ShowReceivedMessages()
    {
        while(!_readingThread.isInterrupted())
        {
            var messageToAppend = _javaCommunicatorClient.Receive();
            if (messageToAppend != null)
            {
                messagesTextBox.append(messageToAppend.get_message());
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
        var message = new Message(0, 0, "hello");
        _javaCommunicatorClient.Send(message);
    }
}
