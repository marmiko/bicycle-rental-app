package gui.controllers.insertion;

import database.daos.*;
import database.objects.Wypozyczenie;
import gui.controllers.choiceTables.ChooseAkcesoriumController;
import gui.controllers.choiceTables.ChooseRowerController;
import gui.controllers.parent.WypozyczenieController;
import gui.controllers.search.SearchModeController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.ButtonUtils;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * controller class for newWypozyczenie.fxml,
 * controls window responsible for inserting new elements of type Wypozyczenie
 */
public class NewWypozyczenieController extends WypozyczenieController {

    @Override
    protected void initializeButtonDodaj(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(klient.getLength() != 0 &&
                        rowerID.getLength() != 0 &&
                        pracownik.getLength() != 0){
                    infoAlert.setText("");
                    Date convertedDataWypozyczenia = null;
                    if(dataWypozyczenia.getValue() != null){
                        convertedDataWypozyczenia = Date.valueOf(dataWypozyczenia.getValue());
                    }
                    long akcesoriumIDLong = -1;
                    try{
                        if(akcesoriumID.getLength()>0){
                            akcesoriumIDLong = Long.parseLong(akcesoriumID.getText());
                        }
                    }  catch (Exception ex) {
                        infoAlert.setText("Błędny format liczby");
                        return;
                    }
                    wypozyczenieDao.insert(new Wypozyczenie(-1, convertedDataWypozyczenia,
                                    null, klient.getText().trim(), Integer.parseInt(pracownik.getText().trim()),
                                    Long.parseLong(rowerID.getText().trim()),
                                    akcesoriumIDLong));
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
