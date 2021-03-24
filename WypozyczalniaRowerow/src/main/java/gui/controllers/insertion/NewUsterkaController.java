package gui.controllers.insertion;

import database.objects.Usterka;
import gui.controllers.parent.UsterkaController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for newUsterka.fxml,
 * controls window responsible for inserting new elements of type Usterka
 */
public class NewUsterkaController extends UsterkaController {

    @Override
    protected void initializeButtonDodaj(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(pracownikWpisujacy.getLength() != 0){
                    infoAlert.setText("");
                    Date convertedDataZgloszenia = null;
                    if(dataZgloszenia.getValue() != null){
                        convertedDataZgloszenia = Date.valueOf(dataZgloszenia.getValue());
                    }
                    long convertedPracownik = -1;
                    try {
                        convertedPracownik = Long.parseLong(pracownikWpisujacy.getText().trim());
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                    usterkaDao.insert(new Usterka(convertedDataZgloszenia, null, convertedPracownik,
                            rowerId, (opis.getLength()!=0?opis.getText().trim():null), -1));
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
