package gui.controllers.search;

import database.objects.PracownikWypozyczalni;
import gui.controllers.parent.PracownikControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for searchPracownik.fxml,
 * controls window responsible for modification of elements of type Pracownik
 */
public class SearchPracownikController extends PracownikControllerModification {

    @FXML TextField id = new TextField();

    public SearchPracownikController(){
        super();
        enterSearchMode();
    }

    @Override
    protected void initializeButton() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Date convertedDataZatrudnienia = null;
                if(dataZatrudnienia.getValue() != null){
                    convertedDataZatrudnienia = Date.valueOf(dataZatrudnienia.getValue());
                }
                Date convertedDataZwolnienia = null;
                if(dataZwolnienia.getValue() != null){
                    convertedDataZwolnienia = Date.valueOf(dataZwolnienia.getValue());
                }
                long convertedId = -1;
                if (id.getLength() != 0) {
                    try {
                        convertedId = Long.parseLong(id.getText().trim());
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                }
                String convertedImie = null;
                if(imie.getLength() != 0){
                    convertedImie = imie.getText().trim();
                }
                String convertedNazwisko = null;
                if(nazwisko.getLength() != 0){
                    convertedNazwisko = nazwisko.getText().trim();
                }
                pracownikWypozyczalniDao.setSearchParams(new PracownikWypozyczalni(convertedImie, convertedNazwisko,
                        convertedId, convertedDataZatrudnienia, convertedDataZwolnienia));
                Stage stage = (Stage) dodaj.getScene().getWindow();
                stage.close();
            }
        });
    }
}
