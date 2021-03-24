package gui.controllers.parent;

import database.daos.ModelRoweruDao;
import gui.controllers.search.SearchModeController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * parent controller class for ModelRoweru objects insertion, modification and serach fxml files,
 * provides basic foundation and common functionality for specialised classes
 */
public abstract class ModelController extends SearchModeController implements Initializable {
    protected ModelRoweruDao modelRoweruDao = new ModelRoweruDao();

    @FXML protected TextField nazwa = new TextField();
    @FXML protected TextField typ = new TextField();
    @FXML protected TextField rozmiar = new TextField();
    @FXML protected TextArea opis = new TextArea();
    @FXML protected Text infoAlert = new Text();
    @FXML protected Button dodaj = new Button();
    @FXML protected TextField cenaZaDzien = new TextField();
    @FXML protected TextField cenaZaMiesiac = new TextField();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeButton();
        infoAlert.setFill(Color.FIREBRICK);
    }


    protected abstract  void initializeButton();
}
