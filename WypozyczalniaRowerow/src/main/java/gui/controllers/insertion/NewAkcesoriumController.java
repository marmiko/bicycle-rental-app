package gui.controllers.insertion;

import database.objects.Akcesorium;
import gui.controllers.parent.AkcesoriumController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import java.sql.Date;

/**
 * controller class for newAkcesorium.fxml,
 * controls window responsible for inserting new elements of type Akcesorium
 */
public class NewAkcesoriumController extends AkcesoriumController {

    protected void initializeButton(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(selectedRodzaj != null){
                    for(int i = 0; i< ilosc.getValue(); i++) {
                        infoAlert.setText("");
                        Date convertedDataZakupu = null;
                        if(dataZakupu.getValue() != null){
                            convertedDataZakupu = Date.valueOf(dataZakupu.getValue());
                        }
                        akcesoriumDao.insert(new Akcesorium(-1, convertedDataZakupu,
                                        selectedRodzaj, null));
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
