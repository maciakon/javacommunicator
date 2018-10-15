package server;

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
            SetStartStopButtonsStates(true);
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
        SetStartStopButtonsStates(false);
        _server.Stop();
    }

    private void SetStartStopButtonsStates(boolean serverStarted)
    {
        if(serverStarted)
        {
            startButton.setDisable(true);
            stopButton.setDisable(false);
        }
        else
        {
            startButton.setDisable(false);
            stopButton.setDisable(true);
        }
    }
}
