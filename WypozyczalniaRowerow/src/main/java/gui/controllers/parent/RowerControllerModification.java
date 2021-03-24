package gui.controllers.parent;

import database.objects.Rower;
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
 * class extending RowerController, which stores common features of modification
 * and search on Rower objects
 */
public abstract class RowerControllerModification extends RowerController {

    protected static Rower orgRower = null;

    @FXML
    Label header = new Label();

    protected String selectedStatus;
    @FXML
    ComboBox<String> status = new ComboBox<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        initalizeComboBoxStatus();
        if(!SearchMode){
            initializeFieldsContents();
        }
    }

    private void initializeFieldsContents(){
        kolor.setText(orgRower.getKolor());
        model.getSelectionModel().select(orgRower.getModel());
        dataZakupu.setValue(orgRower.getDataZakupu().toLocalDate());
        status.getSelectionModel().select(orgRower.getStatus());
    }

    private void initalizeComboBoxStatus(){
        List<String> statusyLst = new ArrayList<>(
                Arrays.asList(DatabaseRestrictedValuesStorage.RowerStatusValues));
        status.setItems(FXCollections.observableList(statusyLst));
        status.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> obs, String was, String is) {
                if(is != null) selectedStatus=is;
            }
        });
    }

    public static void setOrigin(Rower rower){
        orgRower = rower;
    }

    public static void reserOrigin(){
        orgRower = null;
    }
}
