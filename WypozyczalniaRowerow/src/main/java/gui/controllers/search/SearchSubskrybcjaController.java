package gui.controllers.search;

import database.objects.Subskrybcja;
import gui.controllers.parent.SubskrybcjaControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for searchSubskrybcja.fxml,
 * controls window responsible for search of elements of type Subskrybcja
 */
public class SearchSubskrybcjaController extends SubskrybcjaControllerModification {

    public SearchSubskrybcjaController(){
        super();
        enterSearchMode();
    }

    @Override
    protected void initializeButtonDodaj() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Date convertedDataRozpoczecia = null;
                if(dataWypozyczenia.getValue() != null){
                    convertedDataRozpoczecia = Date.valueOf(dataWypozyczenia.getValue());
                }
                Date convertedDataZakonczenia = null;
                if(dataZakonczenia.getValue() != null){
                    convertedDataZakonczenia = Date.valueOf(dataZakonczenia.getValue());
                }
                long akcesoriumIDLong = -1;
                if(akcesoriumID.getLength()>0){
                    akcesoriumIDLong = Long.parseLong(akcesoriumID.getText().trim());
                }
                long pracownikLong = -1;
                if(pracownik.getLength()>0){
                    pracownikLong = Long.parseLong(pracownik.getText().trim());
                }
                long rowerIdLong = -1;
                if(rowerID.getLength()>0){
                    rowerIdLong = Long.parseLong(rowerID.getText().trim());
                }
                String convertedKlient = null;
                if(klient.getLength()>0){
                    convertedKlient = klient.getText().trim();
                }
                subskrybcjaDao.setSearchParams(new Subskrybcja(convertedDataRozpoczecia, convertedKlient, pracownikLong,
                        rowerIdLong, akcesoriumIDLong, convertedDataZakonczenia));
                Stage stage = (Stage) dodaj.getScene().getWindow();
                stage.close();
            }
        });
    }
}
