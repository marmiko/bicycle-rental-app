package gui.controllers.parent;

import database.daos.*;
import gui.controllers.choiceTables.ChooseAkcesoriumController;
import gui.controllers.choiceTables.ChooseRowerController;
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
 * parent controller class for Wypozyczenie objects insertion, modification and serach fxml files,
 * provides basic foundation and common functionality for specialised classes
 */
public abstract class WypozyczenieController extends SearchModeController implements Initializable {

    private static boolean rowerChoosable = true;
    private static String RowerID;
    private static String RowerModel;

    protected RowerDao rowerDao = new RowerDao();
    protected ModelRoweruDao modelRoweruDao = new ModelRoweruDao();
    protected AkcesoriumDao akcesoriumDao = new AkcesoriumDao();
    protected RodzajAkcesoriumDao rodzajAkcesoriumDao = new RodzajAkcesoriumDao();
    protected KlientDao klientDao = new KlientDao();
    protected PracownikWypozyczalniDao pracownikWypozyczalniDao = new PracownikWypozyczalniDao();
    protected WypozyczenieDao wypozyczenieDao = new WypozyczenieDao();

    @FXML protected TextField klient = new TextField();
    @FXML protected TextField rowerID = new TextField();
    @FXML protected TextField rowerModel = new TextField();
    @FXML protected TextField akcesoriumID = new TextField();
    @FXML protected TextField akcesoriumRodzaj = new TextField();
    @FXML protected TextField pracownik = new TextField();
    @FXML protected Text infoAlert = new Text();
    @FXML protected Button wybierzKlienta = new Button();
    @FXML protected Button wybierzRowerID = new Button();
    @FXML protected Button wybierzRowerNazwa = new Button();
    @FXML protected Button wybierzAkcesoriumID = new Button();
    @FXML protected Button wybierzAkcesoriumRodzaj = new Button();
    @FXML protected Button wybierzPracownika = new Button();
    @FXML protected Button dodaj = new Button();
    @FXML protected DatePicker dataWypozyczenia = new DatePicker();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(SearchMode){
            ChooseRowerController.setIfWolneOnly(false);
            ChooseAkcesoriumController.setIfWolneOnly(false);
        }
        else{
            ChooseRowerController.setIfWolneOnly(true);
            ChooseAkcesoriumController.setIfWolneOnly(true);
        }
        infoAlert.setFill(Color.FIREBRICK);
        initializeButtonDodaj();
        ButtonUtils.initializeButtonWybierz(wybierzKlienta, "/fxml/chooseKlient.fxml", klient);
        ButtonUtils.initializeButtonWybierz(wybierzRowerID, "/fxml/chooseRowerID.fxml", rowerID, rowerModel);
        ButtonUtils.initializeButtonWybierz(wybierzRowerNazwa, "/fxml/chooseRowerNazwa.fxml", rowerID, rowerModel);
        ButtonUtils.initializeButtonWybierz(wybierzAkcesoriumID, "/fxml/chooseAkcesoriumID.fxml", akcesoriumID, akcesoriumRodzaj);
        ButtonUtils.initializeButtonWybierz(wybierzAkcesoriumRodzaj, "/fxml/chooseAkcesoriumNazwa.fxml",
                akcesoriumID, akcesoriumRodzaj);
        ButtonUtils.initializeButtonWybierz(wybierzPracownika, "/fxml/choosePracownik.fxml", pracownik);
        if(!rowerChoosable){
            wybierzRowerID.setDisable(true);
            wybierzRowerNazwa.setDisable(true);
            rowerID.setText(RowerID);
            rowerID.setEditable(false);
            rowerModel.setText(RowerModel);
            rowerModel.setEditable(false);
        }
        infoAlert.setFill(Color.FIREBRICK);
    }

    protected abstract void initializeButtonDodaj();

    public static void enterRowerMode(String id, String model){
        rowerChoosable = false;
        RowerID = id;
        RowerModel = model;
    }

    public static void exitRowerMode(){
        rowerChoosable = true;
        RowerID = null;
        RowerModel = null;
    }
}
