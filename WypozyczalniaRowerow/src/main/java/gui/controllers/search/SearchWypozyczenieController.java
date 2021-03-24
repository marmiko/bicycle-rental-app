package gui.controllers.search;

import database.objects.Wypozyczenie;
import gui.controllers.parent.WypozyczenieControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Date;

/**
 * controller class for searchWypozyczenie.fxml,
 * controls window responsible for search of elements of type Wypozyczenie
 */
public class SearchWypozyczenieController extends WypozyczenieControllerModification {

    @FXML TextField wypozyczenieId = new TextField();

    public SearchWypozyczenieController(){
        super();
        enterSearchMode();
    }

    @Override
    protected void initializeButtonDodaj() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Date convertedDataWypozyczenia = null;
                if(dataWypozyczenia.getValue() != null){
                    convertedDataWypozyczenia = Date.valueOf(dataWypozyczenia.getValue());
                }
                Date convertedDataZwrotu = null;
                if(dataZwrotu.getValue() != null){
                    convertedDataZwrotu = Date.valueOf(dataZwrotu.getValue());
                }
                long akcesoriumIDLong = -1;
                if(akcesoriumID.getLength()>0){
                    akcesoriumIDLong = Long.parseLong(akcesoriumID.getText().trim());
                }
                long wypozyczenieIdLong = -1;
                if(wypozyczenieId.getLength()>0){
                    wypozyczenieIdLong = Long.parseLong(wypozyczenieId.getText().trim());
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
                wypozyczenieDao.setSearchParams(new Wypozyczenie(wypozyczenieIdLong, convertedDataWypozyczenia,
                        convertedDataZwrotu, convertedKlient, pracownikLong, rowerIdLong, akcesoriumIDLong));
                Stage stage = (Stage) dodaj.getScene().getWindow();
                stage.close();
            }
        });
    }
}
