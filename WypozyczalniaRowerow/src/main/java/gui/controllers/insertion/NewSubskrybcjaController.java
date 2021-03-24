package gui.controllers.insertion;

import database.objects.Subskrybcja;
import gui.controllers.parent.SubskrybcjaController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for newSubskrybcja.fxml,
 * controls window responsible for inserting new elements of type Subskrybcja
 */
public class NewSubskrybcjaController extends SubskrybcjaController {

    protected void initializeButtonDodaj(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(klient.getLength() != 0 &&
                        rowerID.getLength() != 0 &&
                        pracownik.getLength() != 0){
                    infoAlert.setText("");
                    Date convertedDataRozpoczecia = null;
                    if(dataWypozyczenia.getValue() != null){
                        convertedDataRozpoczecia = Date.valueOf(dataWypozyczenia.getValue());
                    }
                    long convertedIdAkcesorium = -1;
                    long convertedIdPrac = -1;
                    long convertedIdroweru = -1;
                    try{
                        if(akcesoriumID.getLength()>0){
                            convertedIdAkcesorium = Long.parseLong(akcesoriumID.getText());
                        }
                        convertedIdPrac = Long.parseLong(pracownik.getText().trim());
                        convertedIdroweru = Long.parseLong(rowerID.getText().trim());
                    }  catch (Exception ex) {
                        infoAlert.setText("Błędny format liczby");
                        return;
                    }
                    subskrybcjaDao.insert(new Subskrybcja(convertedDataRozpoczecia,
                            klient.getText().trim(), convertedIdPrac,
                            convertedIdroweru,
                            convertedIdAkcesorium, null));
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
