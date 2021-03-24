package gui.controllers.modification;

import database.objects.ModelRoweru;
import gui.controllers.parent.ModelControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import utils.PriceChecker;

/**
 * controller class for modificationModel.fxml,
 * controls window responsible for modification of elements of type ModelRoweru
 */
public class ModifyModelController extends ModelControllerModification {

    @Override
    protected void initializeButton() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(nazwa.getLength()!=0 && typ.getLength()!=0 && rozmiar.getLength()!=0 &&
                cenaZaDzien.getLength() != 0 && cenaZaMiesiac.getLength() != 0){
                    infoAlert.setText("");
                    double cenaMiesiac =orgModel.getCenaZaMiesiac();
                    double cenaDzien =orgModel.getCenaZaDzien();
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

                        modelRoweruDao.update(new ModelRoweru(nazwa.getText().trim(), typ.getText().trim(),
                                rozmiar.getText().trim(), cenaDzien, opis.getText().trim(), cenaMiesiac), orgModel);
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

    public static void setOrigin(ModelRoweru modelRoweru){
        orgModel = modelRoweru;
    }

    public static void resetOrigin(){
        orgModel = null;
    }

}
