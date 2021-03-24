package gui.controllers.insertion;

import database.objects.RodzajAkcesorium;
import gui.controllers.parent.RodzajController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import utils.AlertsUtil;
import utils.PriceChecker;

/**
 * controller class for newRodzaj.fxml,
 * controls window responsible for inserting new elements of type RodzajAkcesorium
 */
public class NewRodzajController extends RodzajController {

    protected void initializeButton(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(nazwa.getLength()!=0){
                    infoAlert.setText("");
                    double cenaMiesiac =-1;
                    double cenaDzien =-1;
                    double kaucjaD = -1;
                    try {
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
                        if (kaucja.getLength() != 0) {
                            try {
                                kaucjaD = PriceChecker.getCena(kaucja);
                            } catch (Exception ex) {
                                infoAlert.setText(ex.getMessage());
                                return;
                            }
                        }

                        rodzajAkcesoriumDao.insert(new RodzajAkcesorium(nazwa.getText().trim(), cenaDzien,
                                cenaMiesiac, kaucjaD));
                    }catch(Exception ex){
                        AlertsUtil.showErrorAlert(null, "Błąd",
                                "Wystąpił błąd podczas dodawania nowego rodzaju do bazy danych.\n" +
                                        "Spróbuj jeszcze raz.");
                    }
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
