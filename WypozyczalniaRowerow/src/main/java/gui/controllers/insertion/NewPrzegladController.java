package gui.controllers.insertion;

import database.objects.Przeglad;
import gui.controllers.parent.PrzegladController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

public class NewPrzegladController extends PrzegladController {

    protected void initializeButtonDodaj(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(pracownik.getLength() != 0){
                    infoAlert.setText("");
                    Date convertedDataWykonania = null;
                    if(dataWykonania.getValue() != null){
                        convertedDataWykonania = Date.valueOf(dataWykonania.getValue());
                    }
                    long convertedPracownik = -1;
                    try {
                        convertedPracownik = Long.parseLong(pracownik.getText().trim());
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                    przegladDao.insert(new Przeglad(convertedDataWykonania,
                            (opis.getLength()!=0?opis.getText().trim():null),
                                    rowerId, convertedPracownik));
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
