package gui.controllers.search;

import database.objects.RodzajAkcesorium;
import gui.controllers.insertion.NewRodzajController;
import gui.controllers.parent.RodzajControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import utils.PriceChecker;

/**
 * controller class for searchRodzaj.fxml,
 * controls window responsible for search of elements of type RodzajAkcesorium
 */
public class SearchRodzajController extends RodzajControllerModification {

    public SearchRodzajController(){
        super();
        enterSearchMode();
    }

    @Override
    protected void initializeButton() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                String convertedNazwa = null;
                if(nazwa.getLength() > 0){
                    convertedNazwa = nazwa.getText().trim();
                }
                double cenaMiesiac = -1;
                double cenaDzien = -1;
                double convertedKaucja = -1;
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
                        cenaDzien = PriceChecker.getCena(cenaZaDzien);
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                }
                if (kaucja.getLength() != 0) {
                    try {
                        convertedKaucja = PriceChecker.getCena(kaucja);
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                }
                rodzajAkcesoriumDao.setSearchParams(new RodzajAkcesorium(convertedNazwa, cenaDzien,
                        cenaMiesiac, convertedKaucja));
                Stage stage = (Stage) dodaj.getScene().getWindow();
                stage.close();
            }
        });
    }

}
