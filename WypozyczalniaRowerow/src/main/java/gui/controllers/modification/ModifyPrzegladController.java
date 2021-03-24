package gui.controllers.modification;

import database.objects.Przeglad;
import gui.controllers.parent.PrzegladControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for modifyPrzeglad.fxml,
 * controls window responsible for modification of elements of type Przeglad
 */
public class ModifyPrzegladController extends PrzegladControllerModification {

    @Override
    protected void initializeButtonDodaj() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(pracownik.getLength() != 0){
                    infoAlert.setText("");
                    Date convertedDataWykonania = orgPrzeglad.getDataWykonania();
                    if(dataWykonania.getValue() != null){
                        convertedDataWykonania = Date.valueOf(dataWykonania.getValue());
                    }
                    przegladDao.update(new Przeglad(convertedDataWykonania, (opis.getLength()!=0?opis.getText():null),
                            rowerId, Long.parseLong(pracownik.getText())), orgPrzeglad);
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
