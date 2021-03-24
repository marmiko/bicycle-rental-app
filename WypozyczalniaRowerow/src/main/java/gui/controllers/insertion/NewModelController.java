package gui.controllers.insertion;

import database.objects.ModelRoweru;
import database.daos.ModelRoweruDao;
import gui.controllers.parent.ModelController;
import gui.controllers.search.SearchModeController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.PriceChecker;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * controller class for newModel.fxml,
 * controls window responsible for inserting new elements of type ModelRoweru
 */
public class NewModelController extends ModelController {

    protected void initializeButton(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(nazwa.getLength()!=0 && typ.getLength()!=0 && rozmiar.getLength()!=0){
                    infoAlert.setText("");
                    double cenaMiesiac =-1;
                    double cenaDzien =-1;
                    try{

                        if(cenaZaMiesiac.getLength()!=0){
                            try {
                                cenaMiesiac = PriceChecker.getCena(cenaZaMiesiac);
                            }catch(Exception ex){
                                infoAlert.setText(ex.getMessage());
                                return;
                            }
                        }

                        if(cenaZaDzien.getLength()!=0){
                            try {
                                cenaDzien = PriceChecker.getCena(cenaZaMiesiac);
                            }catch(Exception ex){
                                infoAlert.setText(ex.getMessage());
                                return;
                            }
                        }

                    modelRoweruDao.insert(new ModelRoweru(nazwa.getText().trim(), typ.getText().trim(),
                            rozmiar.getText().trim(), cenaDzien, opis.getText().trim(), cenaMiesiac));
                    }catch(Exception ex){
                        System.err.println(ex.getMessage());
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
