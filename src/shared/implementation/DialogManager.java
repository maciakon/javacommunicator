package shared.implementation;

import javafx.scene.control.Alert;

public class DialogManager
{
    public static void ShowError(String errorText)
    {
        var alert = new Alert(Alert.AlertType.ERROR, errorText);
        alert.setHeaderText(null);
        alert.setTitle("Error occurred");
        alert.showAndWait();
    }
}
