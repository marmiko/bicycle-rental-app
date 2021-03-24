package gui.controllers.parent;

import database.objects.Usterka;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import utils.ButtonUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * class extending UsterkaController, which stores common features of modification
 * and search on Usterka objects
 */
public abstract class UsterkaControllerModification extends UsterkaController {

    protected static Usterka orgUsterka = null;
    protected @FXML DatePicker dataNaprawy = new DatePicker();
    protected @FXML TextField pracownikNaprawiajacy = new TextField();
    @FXML protected Button wybierzPracownikaNaprawa = new Button();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        ButtonUtils.initializeButtonWybierz(wybierzPracownikaNaprawa,
                "/fxml/choosePracownik.fxml", pracownikNaprawiajacy);
        if(!SearchMode){
            initializeFieldsContents();
            rowerId = orgUsterka.getIdRoweru();
        }
    }

    private void initializeFieldsContents(){
        opis.setText(orgUsterka.getOpis());
        pracownikWpisujacy.setText("" + orgUsterka.getPracownikWpisujacy());
        dataZgloszenia.setValue(orgUsterka.getDataZgloszenia().toLocalDate());
        if(orgUsterka.getPracownikNaprawiajacy() > 0) pracownikNaprawiajacy.setText("" + orgUsterka.getPracownikNaprawiajacy());
        if(orgUsterka.getDataNaprawy() != null) dataNaprawy.setValue(orgUsterka.getDataNaprawy().toLocalDate());
    }

    public static void setOrigin(Usterka usterka){
        orgUsterka = usterka;
    }

    public static void resetOrigin(){
        orgUsterka = null;
    }

}
