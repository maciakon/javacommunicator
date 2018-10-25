package client.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController
{
    private Stage _primaryStage;

    public void ConnectToServer(ActionEvent actionEvent) throws Exception
    {
        var fxmlLoader = new FXMLLoader(getClass().getResource("../mainWindow/client.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        _primaryStage.setScene(scene);
        _primaryStage.show();
    }

    public void setStage(Stage primaryStage)
    {
        _primaryStage = primaryStage;
    }
}
