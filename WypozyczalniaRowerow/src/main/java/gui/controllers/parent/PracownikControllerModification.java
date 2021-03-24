package gui.controllers.parent;

import database.objects.PracownikWypozyczalni;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * class extending PracownikController, which stores common features of modification
 * and search on PracownikWypozyczalni objects
 */
public abstract class PracownikControllerModification extends PracownikController {
    protected static PracownikWypozyczalni orgPracownik = null;

    @FXML
    protected DatePicker dataZwolnienia = new DatePicker();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if(!SearchMode) initializeFieldsContents();
    }

    private void initializeFieldsContents(){
        imie.setText(orgPracownik.getImie());
        nazwisko.setText(orgPracownik.getNazwisko());
        dataZatrudnienia.setValue(orgPracownik.getDataZatrudnienia().toLocalDate());
        if(orgPracownik.getDataZwolnienia() != null)
            dataZwolnienia.setValue(orgPracownik.getDataZwolnienia().toLocalDate());
    }

    public static void setOrigin(PracownikWypozyczalni pracownikWypozyczalni){
        orgPracownik = pracownikWypozyczalni;
    }

    public static void resetOrigin(){
        orgPracownik = null;
    }

}
