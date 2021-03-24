package gui.controllers.modification;

import database.objects.RodzajAkcesorium;
import gui.controllers.parent.RodzajControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import utils.PriceChecker;

/**
 * controller class for modifyRodzaj.fxml,
 * controls window responsible for modify of elements of type RodzajAkcesorium
 */
public class ModifyRodzajController extends RodzajControllerModification {

    @Override
    protected void initializeButton() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(nazwa.getLength()!=0 && cenaZaMiesiac.getLength() != 0 &&
                cenaZaMiesiac.getLength() != 0 && kaucja.getLength() != 0){
                    infoAlert.setText("");
                    double cenaMiesiac =orgRodzaj.getCenaZaMiesiac();
                    double cenaDzien =orgRodzaj.getCenaZaDzien();
                    double kaucjaD = orgRodzaj.getKaucja();
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

                        rodzajAkcesoriumDao.update(new RodzajAkcesorium(nazwa.getText().trim(), cenaDzien,
                                cenaMiesiac, kaucjaD), orgRodzaj);
                    }catch(Exception ex){
                        System.err.println(ex.getMessage());
                    }
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
