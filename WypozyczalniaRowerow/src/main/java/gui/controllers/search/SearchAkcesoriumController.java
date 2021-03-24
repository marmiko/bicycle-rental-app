package gui.controllers.search;

import database.objects.Akcesorium;
import gui.controllers.parent.AkcesoriumControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for searchAkcesorium.fxml,
 * controls window responsible for search of elements of type Akcesorium
 */
public class SearchAkcesoriumController extends AkcesoriumControllerModification {

    @FXML TextField id = new TextField();

    public SearchAkcesoriumController(){
        super();
        enterSearchMode();
    }

    @Override
    protected void initializeButton() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Date convertedDataZakupu = null;
                if(dataZakupu.getValue() != null){
                    convertedDataZakupu = Date.valueOf(dataZakupu.getValue());
                }
                long convertedId = -1;
                if (id.getLength() != 0) {
                    try {
                        convertedId = Long.parseLong(id.getText().trim());
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                }
                akcesoriumDao.setSearchParams(new Akcesorium(convertedId, convertedDataZakupu,
                        selectedRodzaj, selectedStatus));
                Stage stage = (Stage) dodaj.getScene().getWindow();
                stage.close();
            }
        });
    }
}
