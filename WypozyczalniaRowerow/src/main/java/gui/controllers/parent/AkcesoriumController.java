package gui.controllers.parent;

import database.daos.AkcesoriumDao;
import database.daos.RodzajAkcesoriumDao;
import database.objects.Akcesorium;
import database.objects.RodzajAkcesorium;
import gui.controllers.search.SearchModeController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * parent controller class for Akcesorium objects insertion, modification and serach fxml files,
 * provides basic foundation and common functionality for specialised classes
 */
public abstract class AkcesoriumController extends SearchModeController implements Initializable {
    protected AkcesoriumDao akcesoriumDao = new AkcesoriumDao();
    protected RodzajAkcesoriumDao rodzajAkcesoriumDao = new RodzajAkcesoriumDao();

    protected String selectedRodzaj;

    @FXML protected ComboBox<String> rodzaj = new ComboBox<>();
    @FXML protected Text infoAlert = new Text();
    @FXML protected Button dodaj = new Button();
    @FXML protected Spinner<Integer> ilosc = new Spinner<>();
    @FXML protected DatePicker dataZakupu = new DatePicker();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButton();
        initializeComboBoxRodzaj();
        initializeSpinner();
        infoAlert.setFill(Color.FIREBRICK);
    }

    private void initializeComboBoxRodzaj(){
        List<RodzajAkcesorium> rodzajeLst = rodzajAkcesoriumDao.getAll();
        List<String> nazwyLst = new ArrayList<>();
        for(RodzajAkcesorium r : rodzajeLst){
            nazwyLst.add(r.getNazwa());
        }
        rodzaj.setItems(FXCollections.observableList(nazwyLst));
        rodzaj.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> obs, String was, String is) {
                if(is != null) selectedRodzaj=is;
            }
        });
    }

    private void initializeSpinner(){
        ilosc.setEditable(true);
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        ilosc.setValueFactory(valueFactory);
    }

    protected abstract void initializeButton();
}
