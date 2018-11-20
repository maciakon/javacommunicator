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

public class LoginController
{
    @FXML
    TextField _loginTextField;

    private Stage _primaryStage;
    private ClientController _clientController;

    public void ConnectToServer(ActionEvent actionEvent) throws Exception
    {
        var fxmlLoader = new FXMLLoader(getClass().getResource("../mainWindow/client.fxml"));
        Parent root = fxmlLoader.load();
        _clientController = (ClientController)fxmlLoader.getController();
        _clientController.setLogin(_loginTextField.getText());
        Scene scene = new Scene(root);
        _primaryStage.setScene(scene);
        _primaryStage.show();
        _primaryStage.setOnCloseRequest(this::DisconnectOnExit);
    }

    public void DisconnectOnExit(WindowEvent event)
    {
        _clientController.DisconnectOnExit();
    }

    public void setStage(Stage primaryStage)
    {
        _primaryStage = primaryStage;
    }
}
