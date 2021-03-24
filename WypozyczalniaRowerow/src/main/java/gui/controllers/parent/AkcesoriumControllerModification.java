package gui.controllers.parent;

import database.objects.Akcesorium;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import utils.DatabaseRestrictedValuesStorage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * class extending AkcesoriumController, which stores common features of modification and search on Akcesorium objects
 */
public abstract class AkcesoriumControllerModification extends AkcesoriumController {

    protected static Akcesorium orgAkcesorium = null;

    protected String selectedStatus;

    @FXML
    ComboBox<String> status = new ComboBox<>();
    @FXML
    Label header = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        initalizeComboBoxStatus();
        if(!SearchMode){
            initializeFieldsContents();
        }
    }

    private void initializeFieldsContents(){
        dataZakupu.setValue(orgAkcesorium.getDataZakupu().toLocalDate());
        rodzaj.getSelectionModel().select(orgAkcesorium.getRodzaj());
        status.getSelectionModel().select(orgAkcesorium.getStatus());
    }

    private void initalizeComboBoxStatus(){
        List<String> statusyLst = new ArrayList<>(
                Arrays.asList(DatabaseRestrictedValuesStorage.AkcesoriumStatusValues));
        status.setItems(FXCollections.observableList(statusyLst));
        status.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> obs, String was, String is) {
                if(is != null) selectedStatus=is;
            }
        });
    }

    public static void setOrigin(Akcesorium akcesorium){
        orgAkcesorium = akcesorium;
    }

    public static void resetOrigin(){
        orgAkcesorium = null;
    }

}
