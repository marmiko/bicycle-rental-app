package gui.controllers.search;

import database.objects.Przeglad;
import gui.controllers.parent.PrzegladControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for searchPrzeglad.fxml,
 * controls window responsible for search of elements of type Przeglad
 */
public class SearchPrzegladController extends PrzegladControllerModification {

    public SearchPrzegladController(){
        super();
        enterSearchMode();
    }

    @Override
    protected void initializeButtonDodaj() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Date convertedDataWykonania = null;
                if(dataWykonania.getValue() != null){
                    convertedDataWykonania = Date.valueOf(dataWykonania.getValue());
                }
                long convertedPracownik = -1;
                if (pracownik.getLength() != 0) {
                    try {
                        convertedPracownik = Long.parseLong(pracownik.getText().trim());
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                }
                String convertedOpis = null;
                if(opis.getLength() != 0){
                    convertedOpis = opis.getText().trim();
                }
                przegladDao.setSearchParams(new Przeglad(convertedDataWykonania, convertedOpis, rowerId,
                        convertedPracownik));
                Stage stage = (Stage) dodaj.getScene().getWindow();
                stage.close();
            }
        });
    }
}
