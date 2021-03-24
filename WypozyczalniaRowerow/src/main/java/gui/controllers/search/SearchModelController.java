package gui.controllers.search;

import database.objects.ModelRoweru;
import gui.controllers.parent.ModelControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import utils.PriceChecker;

/**
 * controller class for searchModel.fxml,
 * controls window responsible for search of elements of type ModelRoweru
 */
public class SearchModelController extends ModelControllerModification {

    @Override
    protected void initializeButton() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                String convertedNazwa = null;
                if(nazwa.getLength() > 0){
                    convertedNazwa = nazwa.getText().trim();
                }
                String convertedOpis = null;
                if(opis.getLength() > 0){
                    convertedOpis = opis.getText().trim();
                }
                String convertedRozmiar = null;
                if(rozmiar.getLength() > 0){
                    convertedRozmiar = rozmiar.getText().trim();
                }
                String convertedTyp = null;
                if(typ.getLength() > 0){
                    convertedTyp = typ.getText().trim();
                }
                double cenaMiesiac =-1;
                double cenaDzien =-1;
                if (cenaZaMiesiac.getLength() != 0) {
                    try {
                        cenaMiesiac = PriceChecker.getCena(cenaZaMiesiac);
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                }
                if (cenaZaDzien.getLength() != 0) {
                    try {
                        cenaDzien = PriceChecker.getCena(cenaZaMiesiac);
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                }
                modelRoweruDao.setSearchParams(new ModelRoweru(convertedNazwa, convertedTyp, convertedRozmiar,
                        cenaDzien, convertedOpis, cenaMiesiac));
                Stage stage = (Stage) dodaj.getScene().getWindow();
                stage.close();
            }
        });
    }


}
