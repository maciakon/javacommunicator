package client.login;

import client.mainWindow.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController
{
    @FXML
    TextField _loginTextField;

    private Stage _primaryStage;

    public void ConnectToServer(ActionEvent actionEvent) throws Exception
    {
        var fxmlLoader = new FXMLLoader(getClass().getResource("../mainWindow/client.fxml"));
        Parent root = fxmlLoader.load();
        var clientController = (ClientController)fxmlLoader.getController();
        clientController.setLogin(_loginTextField.getText());
        Scene scene = new Scene(root);
        _primaryStage.setScene(scene);
        _primaryStage.show();
    }

    public void setStage(Stage primaryStage)
    {
        _primaryStage = primaryStage;
    }
}
