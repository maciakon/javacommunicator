package client.login;

import client.mainWindow.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Logic for login user interaction.
 */
public class LoginController
{
    @FXML
    TextField _loginTextField;

    private Stage _primaryStage;
    private ClientController _clientController;

    /**
     * Called when a user clicks "Connect" button.
     * <p>Loads client conversation view and closes current login view,
     * <br>
     *     When a conversation view (client.fxml) is loaded, a connection to the server is being established.
     * </p>
     * @param actionEvent not used
     * @throws Exception
     */
    public void ConnectToServer(ActionEvent actionEvent) throws Exception
    {
        var fxmlLoader = new FXMLLoader(getClass().getResource("../mainWindow/client.fxml"));
        Parent root = fxmlLoader.load();
        _clientController = fxmlLoader.getController();
        _clientController.setLogin(_loginTextField.getText());
        Scene scene = new Scene(root);
        _primaryStage.setScene(scene);
        _primaryStage.setTitle(_loginTextField.getText() + "@JavaCommunicator");
        _primaryStage.show();
        _primaryStage.setOnCloseRequest(this::DisconnectOnExit);
    }

    private void DisconnectOnExit(WindowEvent event)
    {
        _clientController.DisconnectOnExit();
    }

    /**
     * Sets new stage, Allows hiding login window.
     * @param primaryStage
     */
    public void setStage(Stage primaryStage)
    {
        _primaryStage = primaryStage;
    }
}
