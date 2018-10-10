package server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServerController
{
    @FXML
    private Button stopButton, startButton;

    public void startButtonClicked()
    {
        startButton.setDisable(true);
        stopButton.setDisable(false);
    }

    public void stopButtonClicked()
    {
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }
}
