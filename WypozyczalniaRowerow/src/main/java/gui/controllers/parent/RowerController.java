package gui.controllers.parent;

import database.objects.ModelRoweru;
import database.daos.ModelRoweruDao;
import database.daos.RowerDao;
import gui.controllers.search.SearchModeController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * parent controller class for Rower objects insertion, modification and serach fxml files,
 * provides basic foundation and common functionality for specialised classes
 */
public abstract class RowerController extends SearchModeController implements Initializable {
    protected RowerDao rowerDao = new RowerDao();
    protected ModelRoweruDao modelRoweruDao = new ModelRoweruDao();

    protected String selectedModel;

    @FXML protected TextField kolor = new TextField();
    @FXML protected ComboBox<String> model = new ComboBox<>();
    @FXML protected Text infoAlert = new Text();
    @FXML protected Button dodaj = new Button();
    @FXML protected Spinner<Integer> ilosc = new Spinner<>();
    @FXML protected DatePicker dataZakupu = new DatePicker();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButton();
        initializeComboBoxModel();
        initializeSpinner();
        infoAlert.setFill(Color.FIREBRICK);
    }

    protected void initializeComboBoxModel(){
        List<ModelRoweru> modeleLst = modelRoweruDao.getAll();
        List<String> nazwyLst = new ArrayList<>();
        for(ModelRoweru m : modeleLst){
            nazwyLst.add(m.getNazwa());
        }
        model.setItems(FXCollections.observableList(nazwyLst));
        model.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> obs, String was, String is) {
                if(is != null) selectedModel=is;
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
