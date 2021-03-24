package gui.controllers.parent;

import database.objects.RodzajAkcesorium;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * class extending RodzajController, which stores common features of modification
 * and search on RodzajAkcesorium objects
 */
public abstract class RodzajControllerModification extends RodzajController {
    protected static RodzajAkcesorium orgRodzaj = null;

    @FXML
    Label header = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if(!SearchMode) initializeFieldsContents();
    }

    private void initializeFieldsContents(){
        nazwa.setText(orgRodzaj.getNazwa());
        cenaZaDzien.setText("" + orgRodzaj.getCenaZaDzien());
        cenaZaMiesiac.setText("" + orgRodzaj.getCenaZaMiesiac());
        kaucja.setText("" + orgRodzaj.getKaucja());
    }

    public static void setOrigin(RodzajAkcesorium rodzajAkcesorium){
        orgRodzaj = rodzajAkcesorium;
    }

    public static void resetOrigin(){
        orgRodzaj = null;
    }
}
