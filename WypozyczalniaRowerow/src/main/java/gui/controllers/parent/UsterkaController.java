package gui.controllers.parent;

import database.daos.UsterkaDao;
import gui.controllers.search.SearchModeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import utils.ButtonUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * parent controller class for Usterka objects insertion, modification and serach fxml files,
 * provides basic foundation and common functionality for specialised classes
 */
public abstract class UsterkaController extends SearchModeController implements Initializable {

    protected static long RowerId;

    protected long rowerId;

    protected UsterkaDao usterkaDao = new UsterkaDao();

    public UsterkaController(){
        this.rowerId = RowerId;
    }

    @FXML
    protected TextField pracownikWpisujacy = new TextField();
    @FXML protected DatePicker dataZgloszenia = new DatePicker();
    @FXML protected TextArea opis = new TextArea();
    @FXML protected Text infoAlert = new Text();

    @FXML protected Button wybierzPracownikaZgloszenie = new Button();
    @FXML protected Button dodaj = new Button();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ButtonUtils.initializeButtonWybierz(wybierzPracownikaZgloszenie, "/fxml/choosePracownik.fxml", pracownikWpisujacy);
        initializeButtonDodaj();
        infoAlert.setFill(Color.FIREBRICK);
    }

    protected abstract void initializeButtonDodaj();

    public static void provideRowerId(long id){
        RowerId = id;
    }

}
