package gui.controllers.search;

import database.objects.Rower;
import gui.controllers.parent.RowerControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

public class SearchRowerController extends RowerControllerModification {
    @FXML TextField rowerId = new TextField();

    public SearchRowerController(){
        super();
        enterSearchMode();
    }

    @Override
    protected void initializeButton() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Date convertedDataZakupu = null;
                if(dataZakupu.getValue() != null){
                    convertedDataZakupu = Date.valueOf(dataZakupu.getValue());
                }
                String convertedKolor = null;
                if(kolor.getLength() > 0){
                    convertedKolor = kolor.getText().trim();
                }
                long rowerIdLong = -1;
                if(rowerId.getLength()>0){
                    rowerIdLong = Long.parseLong(rowerId.getText().trim());
                }
                rowerDao.setSearchParams(new Rower(rowerIdLong, convertedKolor,
                        convertedDataZakupu, selectedModel, selectedStatus));
                Stage stage = (Stage) dodaj.getScene().getWindow();
                stage.close();
            }
        });
    }
}
