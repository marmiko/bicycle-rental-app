package gui.controllers.insertion;

import database.objects.Rower;
import gui.controllers.parent.RowerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for newRower.fxml,
 * controls window responsible for inserting new elements of type Rower
 */
public class NewRowerController extends RowerController {

    protected void initializeButton(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(kolor.getLength()!=0 && selectedModel != null){
                    for(int i = 0; i< ilosc.getValue(); i++) {
                        infoAlert.setText("");
                        Date convertedDataZakupu = null;
                        if(dataZakupu.getValue() != null){
                            convertedDataZakupu = Date.valueOf(dataZakupu.getValue());
                        }
                        rowerDao.insert(new Rower(-1, kolor.getText().trim(),
                                        convertedDataZakupu,
                                selectedModel, null));
                        Stage stage = (Stage) dodaj.getScene().getWindow();
                        stage.close();
                    }
                }
                else{
                    infoAlert.setText("Wypełnij wszystkie pola oznaczone gwiazdką.");
                }
            }

        });
    }
}
