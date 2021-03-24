package gui.controllers.modification;

import database.objects.Subskrybcja;
import gui.controllers.parent.SubskrybcjaControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for modifySubskrybcja.fxml,
 * controls window responsible for modification of elements of type Subskrybcja
 */
public class ModifySubskrybcjaController extends SubskrybcjaControllerModification {

    @Override
    protected void initializeButtonDodaj() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(klient.getLength() != 0 &&
                        rowerID.getLength() != 0 &&
                        pracownik.getLength() != 0){
                    infoAlert.setText("");
                    Date convertedDataRozpoczecia = orgSubskrybcja.getDataRozpoczecia();
                    if(dataWypozyczenia.getValue() != null){
                        convertedDataRozpoczecia = Date.valueOf(dataWypozyczenia.getValue());
                    }
                    Date convertedDataZakonczenia = null;
                    if(dataZakonczenia.getValue() != null){
                        convertedDataZakonczenia = Date.valueOf(dataZakonczenia.getValue());
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
                    String convertedKlient = orgSubskrybcja.getKlientNrDowodu();
                    if(klient.getLength() != 0){
                        convertedKlient = klient.getText().trim();
                    }
                    subskrybcjaDao.update(new Subskrybcja(convertedDataRozpoczecia, convertedKlient, convertedIdPrac,
                            convertedIdroweru, convertedIdAkcesorium, convertedDataZakonczenia), orgSubskrybcja);
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
