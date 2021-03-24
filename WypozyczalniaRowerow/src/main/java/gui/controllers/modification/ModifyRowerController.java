package gui.controllers.modification;

import database.objects.Rower;
import gui.controllers.parent.RowerControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

public class ModifyRowerController extends RowerControllerModification {

    @Override
    protected void initializeButton() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(kolor.getLength()!=0 && selectedModel != null){
                    infoAlert.setText("");
                    Date convertedDataZakupu = orgRower.getDataZakupu();
                    if(dataZakupu.getValue() != null){
                        convertedDataZakupu = Date.valueOf(dataZakupu.getValue());
                    }
                    rowerDao.update(new Rower(orgRower.getId(), kolor.getText().trim(),
                            convertedDataZakupu,
                            selectedModel, selectedStatus.trim()), orgRower);
                    Stage stage = (Stage) dodaj.getScene().getWindow();
                    stage.close();
                    }
                else{
                    infoAlert.setText("Wypełnij wszystkie pola");
                }
            }

        });
    }
}
