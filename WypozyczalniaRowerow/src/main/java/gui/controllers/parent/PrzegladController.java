package gui.controllers.parent;

import database.daos.PrzegladDao;
import gui.controllers.search.SearchModeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.ButtonUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * parent controller class for Przeglad objects insertion, modification and serach fxml files,
 * provides basic foundation and common functionality for specialised classes
 */
public abstract class PrzegladController extends SearchModeController implements Initializable {

    protected static long RowerId;

    protected long rowerId;

    protected PrzegladDao przegladDao = new PrzegladDao();

    @FXML protected TextField pracownik = new TextField();
    @FXML protected DatePicker dataWykonania = new DatePicker();
    @FXML protected TextArea opis = new TextArea();
    @FXML protected Text infoAlert = new Text();

    @FXML protected Button wybierzPracownika = new Button();
    @FXML protected Button dodaj = new Button();

    public PrzegladController(){
        this.rowerId = RowerId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonUtils.initializeButtonWybierz(wybierzPracownika, "/fxml/choosePracownik.fxml", pracownik);
        initializeButtonDodaj();
        infoAlert.setFill(Color.FIREBRICK);
    }

    protected abstract void initializeButtonDodaj();

    public static void provideRowerId(long id){
        RowerId = id;
    }
}
