package gui.controllers.modification;

import database.objects.Akcesorium;
import gui.controllers.parent.AkcesoriumControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for modifyAkcesorium.fxml,
 * controls window responsible for modification of elements of type Akcesorium
 */
public class ModifyAkcesoriumController extends AkcesoriumControllerModification {

    @Override
    protected void initializeButton() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(selectedRodzaj != null){
                    infoAlert.setText("");
                    Date convertedDataZakupu = orgAkcesorium.getDataZakupu();
                    if(dataZakupu.getValue() != null){
                        convertedDataZakupu = Date.valueOf(dataZakupu.getValue());
                    }
                    akcesoriumDao.update(new Akcesorium(orgAkcesorium.getId(), convertedDataZakupu,
                            selectedRodzaj, selectedStatus), orgAkcesorium);
                    Stage stage = (Stage) dodaj.getScene().getWindow();
                    stage.close();
                }
                else{
                    infoAlert.setText("Wypełnij wszystkie pola oznaczone gwiazdką.");
                }
            }

        });
    }

}
