package gui.controllers.parent;

import database.objects.Akcesorium;
import database.objects.Rower;
import database.objects.Subskrybcja;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * class extending SubskrybcjaController, which stores common features of modification
 * and search on Subskrybcja objects
 */
public abstract class SubskrybcjaControllerModification extends SubskrybcjaController {

    protected static Subskrybcja orgSubskrybcja = null;

    @FXML
    protected DatePicker dataZakonczenia = new DatePicker();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        if(!SearchMode) initalizeFieldsContents();
    }

    private void initalizeFieldsContents(){
        klient.setText(orgSubskrybcja.getKlientNrDowodu());
        rowerID.setText("" + orgSubskrybcja.getIdRoweru());
        List<Rower> rowery = rowerDao.get(new Rower(orgSubskrybcja.getIdRoweru(), null, null,
                null, null));
        rowerModel.setText(rowery.get(0).getModel());
        if(orgSubskrybcja.getIdAkcesorium() > 0){
            akcesoriumID.setText("" + orgSubskrybcja.getIdAkcesorium());
            List<Akcesorium> akcesoria = akcesoriumDao.get(new Akcesorium(orgSubskrybcja.getIdAkcesorium(),
                    null, null, null));
            akcesoriumRodzaj.setText(akcesoria.get(0).getRodzaj());
        }
        pracownik.setText("" + orgSubskrybcja.getIdPrac());
        dataWypozyczenia.setValue(orgSubskrybcja.getDataRozpoczecia().toLocalDate());
        if(orgSubskrybcja.getDataZakonczenia() != null)
            dataZakonczenia.setValue(orgSubskrybcja.getDataZakonczenia().toLocalDate());
    }

    public static void setOrigin(Subskrybcja subskrybcja){
        orgSubskrybcja = subskrybcja;
    }

    public static void resetOrigin(){
        orgSubskrybcja = null;
    }
}
