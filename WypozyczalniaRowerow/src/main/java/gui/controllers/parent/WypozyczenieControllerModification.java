package gui.controllers.parent;

import database.objects.Akcesorium;
import database.objects.Rower;
import database.objects.Wypozyczenie;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * class extending WypozyczenieController, which stores common features of modification
 * and search on Wypozyczenie objects
 */
public abstract class WypozyczenieControllerModification extends WypozyczenieController {
    protected static Wypozyczenie orgWypozyczenie = null;

    @FXML
    protected DatePicker dataZwrotu = new DatePicker();
    @FXML protected Label header = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if(!SearchMode) initializeFieldsContents();
    }

    private void initializeFieldsContents(){
        klient.setText(orgWypozyczenie.getKlient_nr_dowodu());
        rowerID.setText("" + orgWypozyczenie.getId_roweru());
        List<Rower> rowery = rowerDao.get(new Rower(orgWypozyczenie.getId_roweru(), null, null,
                null, null));
        rowerModel.setText(rowery.get(0).getModel());
        if(orgWypozyczenie.getId_akcesoria() > 0){
            akcesoriumID.setText("" + orgWypozyczenie.getId_akcesoria());
            List<Akcesorium> akcesoria = akcesoriumDao.get(new Akcesorium(orgWypozyczenie.getId_akcesoria(),
                    null, null, null));
            akcesoriumRodzaj.setText(akcesoria.get(0).getRodzaj());
        }
        pracownik.setText("" + orgWypozyczenie.getId_prac());
        dataWypozyczenia.setValue(orgWypozyczenie.getData_wypozyczenia().toLocalDate());
        if(orgWypozyczenie.getData_zwrotu() != null)
            dataZwrotu.setValue(orgWypozyczenie.getData_zwrotu().toLocalDate());
    }

    public static void setOrigin(Wypozyczenie wypozyczenie){
        orgWypozyczenie = wypozyczenie;
    }

    public static void resetOrigin(){
        orgWypozyczenie = null;
    }
}
