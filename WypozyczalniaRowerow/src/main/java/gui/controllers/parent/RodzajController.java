package gui.controllers.parent;

import database.daos.RodzajAkcesoriumDao;
import database.objects.RodzajAkcesorium;
import gui.controllers.search.SearchModeController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.AlertsUtil;
import utils.PriceChecker;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * parent controller class for RodzajAkcesorium objects insertion, modification and serach fxml files,
 * provides basic foundation and common functionality for specialised classes
 */
public abstract class RodzajController extends SearchModeController implements Initializable {
    protected RodzajAkcesoriumDao rodzajAkcesoriumDao = new RodzajAkcesoriumDao();

    @FXML protected TextField nazwa = new TextField();
    @FXML protected Text infoAlert = new Text();
    @FXML protected Button dodaj = new Button();
    @FXML protected TextField cenaZaDzien = new TextField();
    @FXML protected TextField cenaZaMiesiac = new TextField();
    @FXML protected TextField kaucja = new TextField();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButton();
        infoAlert.setFill(Color.FIREBRICK);
    }

    protected abstract void initializeButton();
}
