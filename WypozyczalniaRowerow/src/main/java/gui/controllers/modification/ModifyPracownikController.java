package gui.controllers.modification;

import database.objects.PracownikWypozyczalni;
import gui.controllers.parent.PracownikControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for modifyPracownik.fxml,
 * controls window responsible for modification of elements of type PracownikWypozyczalni
 */
public class ModifyPracownikController extends PracownikControllerModification {

    @Override
    protected void initializeButton(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(imie.getLength() != 0 &&
                        nazwisko.getLength() != 0){
                    infoAlert.setText("");
                    Date convertedDataZatrudnienia = orgPracownik.getDataZatrudnienia();
                    if(dataZatrudnienia.getValue() != null){
                        convertedDataZatrudnienia = Date.valueOf(dataZatrudnienia.getValue());
                    }
                    Date convertedDataZwolnienia = null;
                    if(dataZwolnienia.getValue() != null){
                        convertedDataZwolnienia = Date.valueOf(dataZwolnienia.getValue());
                    }
                    pracownikWypozyczalniDao.update(new PracownikWypozyczalni(imie.getText().trim(),
                            nazwisko.getText().trim(), orgPracownik.getId(),
                            convertedDataZatrudnienia, convertedDataZwolnienia), orgPracownik);
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
