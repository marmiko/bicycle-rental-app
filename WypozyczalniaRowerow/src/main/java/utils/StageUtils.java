package utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * utility class used to create and manage Stages
 */
public class StageUtils {

    public static Stage createNewStage(Parent root, String title){
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        return stage;
    }
}
