package gui.controllers.modification;

import database.objects.Klient;
import gui.controllers.parent.KlientControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

/**
 * controller class for modifyKlient.fxml,
 * controls window responsible for modification of elements of type Klient
 */
public class ModifyKlientController extends KlientControllerModification {

    @Override
    protected void initializeButtonDodaj() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if (nrDowodu.getLength() != 0 && imie.getLength() != 0 &&
                        nazwisko.getLength() != 0 && nrTelefonu.getLength() != 0) {
                    infoAlert.setText("");
                    int nrTelefonuInt = orgKlient.getNr_telefonu();
                    if (nrTelefonu.getLength() > 0) {
                        nrTelefonuInt = Integer.parseInt(nrTelefonu.getText().trim());
                    }
                    klientDao.update(new Klient(nrDowodu.getText().trim(), imie.getText().trim(),
                                    nazwisko.getText().trim(), nrTelefonuInt),
                            orgKlient);
                    Stage stage = (Stage) dodaj.getScene().getWindow();
                    stage.close();
                } else {
                    infoAlert.setText("Wypełnij wszystkie pola");
                }
            }

        });
    }
}
