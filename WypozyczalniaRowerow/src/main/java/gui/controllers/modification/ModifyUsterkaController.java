package gui.controllers.modification;

import database.objects.Usterka;
import gui.controllers.parent.UsterkaControllerModification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * controller class for modifyUsterka.fxml,
 * controls window responsible for modification of elements of type Usterka
 */
public class ModifyUsterkaController extends UsterkaControllerModification {

    @Override
    protected void initializeButtonDodaj() {
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(pracownikWpisujacy.getLength() != 0){
                    infoAlert.setText("");
                    Date convertedDataZgloszenia = orgUsterka.getDataZgloszenia();
                    if(dataZgloszenia.getValue() != null){
                        convertedDataZgloszenia = Date.valueOf(dataZgloszenia.getValue());
                    }
                    Date convertedDataNaprawy = null;
                    if(dataNaprawy.getValue() != null){
                        convertedDataNaprawy = Date.valueOf(dataNaprawy.getValue());
                    }
                    long convertedPracownikWpisujacy;
                    try {
                        convertedPracownikWpisujacy = Long.parseLong(pracownikWpisujacy.getText().trim());
                    } catch (Exception ex) {
                        infoAlert.setText(ex.getMessage());
                        return;
                    }
                    long convertedPracownikNaprawiajacy = -1;
                    if(pracownikNaprawiajacy.getLength() != 0){
                        try {
                            convertedPracownikNaprawiajacy = Long.parseLong(pracownikNaprawiajacy.getText().trim());
                        } catch (Exception ex) {
                            infoAlert.setText(ex.getMessage());
                            return;
                        }
                    }
                    System.out.println("rowerId = " + rowerId);
                    usterkaDao.update(new Usterka(convertedDataZgloszenia, convertedDataNaprawy,
                            convertedPracownikWpisujacy, rowerId, (opis.getLength()!=0?opis.getText().trim():null),
                            convertedPracownikNaprawiajacy), orgUsterka);
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
