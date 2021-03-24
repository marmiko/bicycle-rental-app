package gui.controllers.insertion;

import database.objects.Klient;
import gui.controllers.parent.KlientController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;


/**
 * controller class for newKlient.fxml,
 * controls window responsible for inserting new elements of type Klient
 */
public class NewKlientController extends KlientController {

    protected void initializeButtonDodaj(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                int convertedNrTelefonu = 0;
                try {
                    convertedNrTelefonu = Integer.parseInt(nrTelefonu.getText());
                }catch (NumberFormatException ex){
                    infoAlert.setText("Numer telefonu musi być liczbą.");
                    return;
                }
                if(nrDowodu.getLength()!=0 && imie.getLength()!=0 && nazwisko.getLength()!=0 &&
                        nrTelefonu.getLength()!=0 && convertedNrTelefonu > 0){
                    infoAlert.setText("");
                    klientDao.insert(new Klient(nrDowodu.getText(), imie.getText(), nazwisko.getText(),
                            convertedNrTelefonu));
                    Stage stage = (Stage) dodaj.getScene().getWindow();
                    stage.close();
                }
                else{
                    infoAlert.setText("Wypełnij wszystkie pola.");
                }
            }

        });
    }
}
