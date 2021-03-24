package gui.controllers.parent;

import database.objects.Klient;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * class extending KlientController, which stores common features of modification and search on Klient objects
 */
public abstract class KlientControllerModification extends KlientController {

    protected static Klient orgKlient = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if(!SearchMode) initaizeFieldsContents();
    }

    private void initaizeFieldsContents(){
        nrDowodu.setText(orgKlient.getNr_dowodu());
        imie.setText(orgKlient.getImie());
        nazwisko.setText(orgKlient.getNazwisko());
        nrTelefonu.setText("" + orgKlient.getNr_telefonu());
    }

    public static void setOrigin(Klient klient){
        orgKlient = klient;
    }

    public static void resetOrigin(){
        orgKlient = null;
    }
}
