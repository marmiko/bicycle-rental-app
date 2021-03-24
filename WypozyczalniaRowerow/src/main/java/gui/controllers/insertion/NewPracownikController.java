package gui.controllers.insertion;

import database.objects.PracownikWypozyczalni;
import gui.controllers.parent.PracownikController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for newPracownik.fxml,
 * controls window responsible for inserting new elements of type PracownikWypozyczalni
 */
public class NewPracownikController extends PracownikController {

    protected void initializeButton(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(imie.getLength()!=0 && nazwisko.getLength()!=0){
                    infoAlert.setText("");
                    Date convertedDataZatrudnienia = null;
                    if(dataZatrudnienia.getValue() != null){
                        convertedDataZatrudnienia = Date.valueOf(dataZatrudnienia.getValue());
                    }
                    pracownikWypozyczalniDao.insert(new PracownikWypozyczalni(imie.getText(), nazwisko.getText(), -1,
                            convertedDataZatrudnienia, null));
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
