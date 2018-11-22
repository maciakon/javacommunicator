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

/**
 * Contains logic for conversation purposes.
 * <p>The conversation view consists of a contact list and a tab container, </p>
 * <p<Each conversation has its own tab assigned./p>
 */
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

    /**
     * Sets login and starts {@link JavaCommunicatorClient} that connects to the server.
     * <p>After a connection has been established, a new thread is spawn to actively wait for incoming messages, </p>
     * @param login passed to the {@link JavaCommunicatorClient}.
     */
    public void setLogin(String login)
    {
        _javaCommunicatorClient = new JavaCommunicatorClient(_host, _portNumber, login);
        _javaCommunicatorClient.Start();
        _readingThread = new Thread(this::ProcessReceivedMessages);
        _readingThread.start();
    }

    /**
     * Called when a user double clicks a contacts list.
     * <p>Opens a new conversation tab and assigns it an index from the contacts list.
     * That is needed because there may be contacts with the same name displayed on the list and application needs to distinguish them.</p>
     * @param mouseEvent used for checking if user double clicked an contact list item.
     */
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

    /**
     * Adds new tab to the conversation tab pane, Each tab is assigned with its own controller.
     * @param contactIndex distinguish between contacts with the same name in the contact list.
     */
    public void AddTab(int contactIndex)
    {
        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/tab.fxml"));
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

    /**
     * Receiving messages thread method.
     * Actively waits for an incoming message.
     * Because it gets items from a blocking queue, it is needed to pass it a {@link PoisonedPillMessage} to interrupt waiting.
     * <p> Whenever a message is taken from the queue, it is passed to {@link ClientHandlerFactory} to get appropriate handler.
     * That allows encapsulation of message handling logic inside of a specific handler for a message type.</p>
     */
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
