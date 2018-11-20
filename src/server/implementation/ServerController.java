package server.implementation;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import server.interfaces.IJavaCommunicatorServer;
import shared.implementation.DialogManager;
import shared.interfaces.IPortValidator;
import shared.implementation.PortValidator;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerController
{
    @FXML
    private Button stopButton, startButton;
    @FXML
    private TextField portNumberText;

    private IJavaCommunicatorServer _server;
    private final IPortValidator _portNumberValidator;

    public ServerController()
    {
        _portNumberValidator = new PortValidator();
    }

    /**
     * Handles start button click.
     * Set enabled/disabled controls state.
     * Starts the server.
     * @throws IOException
     */
    public void startButtonClicked() throws IOException
    {
        var portNumber = _portNumberValidator.GetPortNumberFromString(portNumberText.getText());
        if(portNumber > 0)
        {
            SetControlsState(true);
            _server = new JavaCommunicatorServer(new ServerSocket(portNumber));
            _server.Start();
        }
        else
        {
            DialogManager.ShowError("Invalid port number.");
        }
    }

    /**
     * Stops server. Changes controls state to editable.
     */
    public void stopButtonClicked()
    {
        SetControlsState(false);
        portNumberText.setEditable(true);
        _server.Stop();
    }

    private void SetControlsState(boolean serverStarted)
    {
        if(serverStarted)
        {
            portNumberText.setEditable(false);
            portNumberText.setDisable(true);
            startButton.setDisable(true);
            stopButton.setDisable(false);
        }
        else
        {
            portNumberText.setEditable(true);
            portNumberText.setDisable(false);
            startButton.setDisable(false);
            stopButton.setDisable(true);
        }
    }
    @FXML
    protected void initialize()
    {
        Platform.runLater(() ->
        {
            portNumberText.setText("4441");
            portNumberText.requestFocus();
        });
    }
}
