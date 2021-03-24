package gui.controllers.parent;

import database.daos.PracownikWypozyczalniDao;
import gui.controllers.search.SearchModeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * parent controller class for PracownikWypozyczalni objects insertion, modification and serach fxml files,
 * provides basic foundation and common functionality for specialised classes
 */
public abstract class PracownikController extends SearchModeController implements Initializable {
    protected PracownikWypozyczalniDao pracownikWypozyczalniDao = new PracownikWypozyczalniDao();

    @FXML protected TextField imie = new TextField();
    @FXML protected TextField nazwisko = new TextField();
    @FXML protected Button dodaj = new Button();
    @FXML protected Text infoAlert  = new Text();
    @FXML protected DatePicker dataZatrudnienia = new DatePicker();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButton();
        infoAlert.setFill(Color.FIREBRICK);
    }

    protected abstract void initializeButton();
}
