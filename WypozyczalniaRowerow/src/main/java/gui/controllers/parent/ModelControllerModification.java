package gui.controllers.parent;

import database.objects.ModelRoweru;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * class extending ModelController, which stores common features of modification and search on ModelRoweru objects
 */
public abstract class ModelControllerModification extends ModelController {

    protected static ModelRoweru orgModel = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        initializeFieldsContents();
    }

    private void initializeFieldsContents(){
        nazwa.setText(orgModel.getNazwa());
        rozmiar.setText(orgModel.getRozmiar());
        typ.setText(orgModel.getTyp());
        opis.setText(orgModel.getOpis());
        cenaZaDzien.setText("" + orgModel.getCenaZaDzien());
        cenaZaMiesiac.setText("" + orgModel.getCenaZaMiesiac());
    }

    public static void setOrigin(ModelRoweru modelRoweru){
        orgModel = modelRoweru;
    }

    public static void resetOrigin(){
        orgModel = null;
    }

}
