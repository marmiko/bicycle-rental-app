package utils;

import javafx.scene.control.Alert;

/**
 * utility class used to popup alert windows
 */
public class AlertsUtil {

    /**
     * shows alert Error window with provided message
     * @param header alert window header text - if value is null then header is not displayed
     * @param title alert window title
     * @param msg alert window content text
     */
    public static void showErrorAlert(String header, String title, String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
