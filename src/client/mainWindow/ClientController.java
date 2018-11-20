package client.mainWindow;

import client.mainWindow.messageHandlers.ClientHandlerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import shared.implementation.messages.PoisonedPillMessage;

import java.io.IOException;

public class ClientController
{
    private JavaCommunicatorClient _javaCommunicatorClient;
    private Thread _readingThread;

    @FXML
    ListView contactsList;
    @FXML
    TabPane conversationTabPane;

    private static final  int _portNumber = 4441;
    private static final String _host = "localhost";

    public void setLogin(String login)
    {
        _javaCommunicatorClient = new JavaCommunicatorClient(_host, _portNumber, login);
        _javaCommunicatorClient.Start();
        _readingThread = new Thread(this::ProcessReceivedMessages);
        _readingThread.start();
    }

    public void ListViewMouseClicked(MouseEvent mouseEvent)
    {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
        {
            if (mouseEvent.getClickCount() == 2)
            {
                var contactIndex = contactsList.getSelectionModel().getSelectedIndex();
                AddTab(contactIndex);
            }
        }
    }

    public void AddTab(int contactIndex)
    {
        var fxmlLoader = new FXMLLoader(getClass().getResource("tab.fxml"));
        var tabControllerFactory = new TabControllerFactory(_javaCommunicatorClient, contactIndex);
        fxmlLoader.setControllerFactory(tabControllerFactory);

        try
        {
            var root = fxmlLoader.load();
            conversationTabPane.getTabs().add((Tab)root);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void ProcessReceivedMessages()
    {
        while(!_readingThread.isInterrupted())
        {
            var message = _javaCommunicatorClient.Receive();
            if(message instanceof PoisonedPillMessage)
            {
                // interrupt thread and stop blocking queue from waiting for items
                return;
            }

            if (message != null)
            {
                var handlerFactory = new ClientHandlerFactory(contactsList, this, _javaCommunicatorClient);
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
                break;
            }
        }
    }

    public void DisconnectOnExit()
    {
        _readingThread.interrupt();
        _javaCommunicatorClient.Stop();
    }
}
