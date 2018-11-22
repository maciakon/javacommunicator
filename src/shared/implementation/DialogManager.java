package shared.implementation;

import javafx.scene.control.Alert;

/**
 * Encapsulates showing error dialog process.
 */
public class DialogManager
{
    /**
     * Shows error message box with custom text.
     * @param errorText error text to be displayed in the window
     */
    public static void ShowError(String errorText)
    {
        var alert = new Alert(Alert.AlertType.ERROR, errorText);
        alert.setHeaderText(null);
        alert.setTitle("Error occurred");
        alert.showAndWait();
    }
}
