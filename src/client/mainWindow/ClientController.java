package client.mainWindow;

import client.mainWindow.messageHandlers.ClientHandlerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;

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
                var fxmlLoader = new FXMLLoader(getClass().getResource("tab.fxml"));
                try
                {
                    var root = fxmlLoader.load();
                    conversationTabPane.getTabs().add((Tab)root);
                    var tabController =  (TabController)fxmlLoader.getController();
                    String contactName = (String)contactsList.getSelectionModel().getSelectedItem();
                    tabController.setName(contactName);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void SendMessage(ActionEvent actionEvent)
    {
        var x = actionEvent;
        var textField = (TextField)actionEvent.getSource();
        var text = textField.getText();
        //read the message from the text field
        //get current receiver
        //send actual message
    }

    private void ShowReceivedMessages()
    {
        while(!_readingThread.isInterrupted())
        {
            var message = _javaCommunicatorClient.Receive();

            if (message != null)
            {
                var handlerFactory = new ClientHandlerFactory(contactsList);
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
}
