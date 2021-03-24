package gui.controllers.parent;

import database.daos.KlientDao;
import gui.controllers.search.SearchModeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * parent controller class for Klient objects insertion, modification and serach fxml files,
 * provides basic foundation and common functionality for specialised classes
 */
public abstract class KlientController extends SearchModeController implements Initializable {
    protected KlientDao klientDao = new KlientDao();

    @FXML protected TextField nrDowodu = new TextField();
    @FXML protected TextField imie = new TextField();
    @FXML protected TextField nazwisko = new TextField();
    @FXML protected TextField nrTelefonu = new TextField();
    @FXML protected Text infoAlert = new Text();
    @FXML protected Button dodaj = new Button();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButtonDodaj();
        infoAlert.setFill(Color.FIREBRICK);
    }

    protected abstract void initializeButtonDodaj();
}
