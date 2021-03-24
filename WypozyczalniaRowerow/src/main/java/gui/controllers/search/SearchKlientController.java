package gui.controllers.search;

import database.objects.Klient;
import gui.controllers.parent.KlientControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * controller class for searchKlient.fxml,
 * controls window responsible for search of elements of type Klient
 */
public class SearchKlientController extends KlientControllerModification {

    public SearchKlientController(){
        super();
        enterSearchMode();
    }

    @Override
    protected void initializeButtonDodaj() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                int convertedNrTelefonu = -1;
                if (nrTelefonu.getLength() != 0) {
                    try {
                        convertedNrTelefonu = Integer.parseInt(nrTelefonu.getText().trim());
                    } catch (Exception ex) {
                        infoAlert.setText("Błędny format liczby");
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
                String convertednNrDowodu = null;
                if(nrDowodu.getLength() != 0){
                    convertednNrDowodu = nrDowodu.getText().trim();
                }
                klientDao.setSearchParams(new Klient(convertednNrDowodu, convertedImie, convertedNazwisko,
                        convertedNrTelefonu));
                Stage stage = (Stage) dodaj.getScene().getWindow();
                stage.close();
            }
        });
    }
}
