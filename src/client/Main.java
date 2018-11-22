package client;

import client.login.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the client application.
 */
public class Main extends Application
{
    /**
     * Starts client application.
     * @param primaryStage main stage of the application
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        var fxmlLoader = new FXMLLoader(getClass().getResource("resources/login.fxml"));
        Parent root = fxmlLoader.load();
        var loginController = fxmlLoader.getController();
        ((LoginController)loginController).setStage(primaryStage);
        primaryStage.setTitle("JavaCommunicator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
