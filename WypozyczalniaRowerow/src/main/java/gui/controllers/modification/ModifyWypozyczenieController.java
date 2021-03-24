package gui.controllers.modification;

import database.objects.Wypozyczenie;
import gui.controllers.parent.WypozyczenieControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for modifyWypozyczenie.fxml,
 * controls window responsible for modification of elements of type Wypozyczenie
 */
public class ModifyWypozyczenieController extends WypozyczenieControllerModification {

    @Override
    protected void initializeButtonDodaj(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(klient.getLength() != 0 &&
                        rowerID.getLength() != 0 &&
                        pracownik.getLength() != 0){
                    infoAlert.setText("");
                    Date convertedDataWypozyczenia = orgWypozyczenie.getData_wypozyczenia();
                    if(dataWypozyczenia.getValue() != null){
                        convertedDataWypozyczenia = Date.valueOf(dataWypozyczenia.getValue());
                    }
                    Date convertedDataZwrotu = null;
                    if(dataZwrotu.getValue() != null){
                        convertedDataZwrotu = Date.valueOf(dataZwrotu.getValue());
                    }
                    long akcesoriumIDLong = orgWypozyczenie.getId_akcesoria();
                    if(akcesoriumID.getLength()>0){
                        akcesoriumIDLong = Long.parseLong(akcesoriumID.getText().trim());
                    }
                    wypozyczenieDao.update(new Wypozyczenie(orgWypozyczenie.getId_wypozyczenia(),
                            convertedDataWypozyczenia, convertedDataZwrotu, klient.getText().trim(),
                            Long.parseLong(pracownik.getText().trim()), Long.parseLong(rowerID.getText().trim()),
                            akcesoriumIDLong), orgWypozyczenie);
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
