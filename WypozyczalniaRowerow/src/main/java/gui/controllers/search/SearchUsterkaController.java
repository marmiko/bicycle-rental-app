package gui.controllers.search;

import database.objects.Usterka;
import gui.controllers.parent.UsterkaControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for searchUsterka.fxml,
 * controls window responsible for search of elements of type Usterka
 */
public class SearchUsterkaController extends UsterkaControllerModification {

    public SearchUsterkaController(){
        super();
        enterSearchMode();
    }

    @Override
    protected void initializeButtonDodaj() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Date convertedDataZgloszenia = null;
                if(dataZgloszenia.getValue() != null){
                    convertedDataZgloszenia = Date.valueOf(dataZgloszenia.getValue());
                }
                Date convertedDataNaprawy = null;
                if(dataNaprawy.getValue() != null){
                    convertedDataNaprawy = Date.valueOf(dataNaprawy.getValue());
                }
                long convertedPracownikWpisujacy = -1;
                if (pracownikWpisujacy.getLength() != 0) {
                    try {
                        convertedPracownikWpisujacy = Long.parseLong(pracownikWpisujacy.getText().trim());
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                }
                long convertedPracownikNaprawiajacy = -1;
                if (pracownikNaprawiajacy.getLength() != 0) {
                    try {
                        convertedPracownikNaprawiajacy = Long.parseLong(pracownikNaprawiajacy.getText().trim());
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                }
                String convertedOpis = null;
                if(opis.getLength() != 0){
                    convertedOpis = opis.getText().trim();
                }
                usterkaDao.setSearchParams(new Usterka(convertedDataZgloszenia, convertedDataNaprawy,
                        convertedPracownikWpisujacy, rowerId, convertedOpis, convertedPracownikNaprawiajacy));
                Stage stage = (Stage) dodaj.getScene().getWindow();
                stage.close();
            }
        });
    }
}
