package server;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import shared.DialogManager;
import shared.IPortValidator;
import shared.PortValidator;
import java.io.IOException;
import java.net.ServerSocket;

public class ServerController
{
    @FXML
    private Button stopButton, startButton;
    @FXML
    private TextField portNumberText;

    private IJavaCommunicatorServer _server;
    private IPortValidator _portNumberValidator;

    public ServerController()
    {
        _portNumberValidator = new PortValidator();
    }

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
            DialogManager.ShowError("Invalid port number");
        }
    }

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
