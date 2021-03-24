package gui.controllers.parent;

import database.objects.Przeglad;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * class extending PrzegladController, which stores common features of modification
 * and search on Przeglad objects
 */
public abstract class PrzegladControllerModification extends PrzegladController {
    protected static Przeglad orgPrzeglad = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if(!SearchMode){
            initializeFieldsContents();
            rowerId = orgPrzeglad.getIdRoweru();
        }
    }

    private void initializeFieldsContents(){
        opis.setText(orgPrzeglad.getOpis());
        pracownik.setText("" + orgPrzeglad.getPracownik());
        dataWykonania.setValue(orgPrzeglad.getDataWykonania().toLocalDate());
    }

    public static void setOrigin(Przeglad przeglad){
        orgPrzeglad = przeglad;
    }

    public static void resetOrigin(){
        orgPrzeglad = null;
    }
}
