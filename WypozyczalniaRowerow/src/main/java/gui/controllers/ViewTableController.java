package gui.controllers;

import database.daos.*;
import database.objects.*;
import gui.controllers.insertion.NewWypozyczenieController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.AlertsUtil;
import utils.ButtonUtils;
import utils.StageUtils;
import utils.TableUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * controller class for main.fxml, controls the main window of the application
 */
public class ViewTableController implements Initializable {

    @FXML private Button nowyKlient = new Button();
    @FXML private Button nowyPracownik = new Button();
    @FXML private Button nowyRower = new Button();
    @FXML private Button noweAkcesorium = new Button();
    @FXML private Button noweWypozyczenie = new Button();
    @FXML private Button nowyModel = new Button();
    @FXML private Button nowyRodzaj = new Button();
    @FXML private Button nowaSubskrybcja = new Button();

    @FXML private Button refreshWypozyczenia = new Button();
    @FXML private Button refreshKlienci = new Button();
    @FXML private Button refreshRowery = new Button();
    @FXML private Button refreshAkcesoria = new Button();
    @FXML private Button refreshPracownicy = new Button();
    @FXML private Button refreshModele = new Button();
    @FXML private Button refreshRodzaje = new Button();
    @FXML private Button refreshSubskrybcje = new Button();

    @FXML private Button usunWypozyczenia = new Button();
    @FXML private Button usunKlienci = new Button();
    @FXML private Button usunRowery = new Button();
    @FXML private Button usunAkcesoria = new Button();
    @FXML private Button usunPracownicy = new Button();
    @FXML private Button usunModele = new Button();
    @FXML private Button usunRodzaje = new Button();
    @FXML private Button usunSubskrybcje = new Button();

    @FXML private Button wyszukajWypozyczenia = new Button();
    @FXML private Button wyszukajKlienci = new Button();
    @FXML private Button wyszukajRowery = new Button();
    @FXML private Button wyszukajAkcesoria = new Button();
    @FXML private Button wyszukajPracownicy = new Button();
    @FXML private Button wyszukajModele = new Button();
    @FXML private Button wyszukajRodzaje = new Button();
    @FXML private Button wyszukajSubskrybcje = new Button();

    @FXML private Button modyfikujWypozyczenie = new Button();
    @FXML private Button modyfikujKlient = new Button();
    @FXML private Button modyfikujRower = new Button();
    @FXML private Button modyfikujAkcesorium = new Button();
    @FXML private Button modyfikujPracownik = new Button();
    @FXML private Button modyfikujModel = new Button();
    @FXML private Button modyfikujRodzaj = new Button();
    @FXML private Button modyfikujSubskrybcja = new Button();

    @FXML private Button zwrocWypozyczenia = new Button();
    @FXML private Button zakonczSubskrybcje = new Button();
    @FXML private Button wypozyczRower = new Button();
    @FXML private Button wypozyczModel = new Button();
    @FXML private Button przeglady = new Button();
    @FXML private Button usterki = new Button();

    @FXML private CheckBox aktywneWypozyczenia = new CheckBox();
    @FXML private CheckBox aktywneSubskrybcje = new CheckBox();

    private WypozyczenieDao wypozyczenieDao;
    private KlientDao klientDao;
    private RowerDao rowerDao;
    private AkcesoriumDao akcesoriumDao;
    private PracownikWypozyczalniDao pracownikWypozyczalniDao;
    private ModelRoweruDao modelRoweruDao;
    private RodzajAkcesoriumDao rodzajAkcesoriumDao;
    private SubskrybcjaDao subskrybcjaDao;

    @FXML private TableView<Wypozyczenie> wypozyczeniaTable;
    @FXML private TableColumn<Wypozyczenie, Boolean> checkedColumnWypozyczenia;

    @FXML private TableView<Klient> klienciTable;
    @FXML private TableColumn<Klient, Boolean> checkedColumnKlienci;

    @FXML private TableView<Rower> roweryTable;
    @FXML private TableColumn<Rower, Boolean> checkedColumnRowery;

    @FXML private TableView<Akcesorium> akcesoriaTable;
    @FXML private TableColumn<Akcesorium, Boolean> checkedColumnAkcesoria;

    @FXML private TableView<PracownikWypozyczalni> pracownicyTable;
    @FXML private TableColumn<PracownikWypozyczalni, Boolean> checkedColumnPracownicy;

    @FXML private TableView<ModelRoweru> modeleRowerowTable;
    @FXML private TableColumn<ModelRoweru, Boolean> checkedColumnModele;

    @FXML private TableView<RodzajAkcesorium> rodzajAkcesoriumTable;
    @FXML private TableColumn<RodzajAkcesorium, Boolean> checkedColumnRodzaje;

    @FXML private TableView<Subskrybcja> subskrybcjeTable;
    @FXML private TableColumn<Subskrybcja, Boolean> checkedColumnSubskrybcje;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        initializeWypozyczenia();
        initializeKlienci();
        initializeRowery();
        initializeAkcesoria();
        initializePracownicy();
        initializeModeleRowerow();
        initializeRodzajeAkcesorium();
        initializeSubskrybcje();
        initializeButtons();
    }

    private void initializeWypozyczenia(){
        wypozyczenieDao = new WypozyczenieDao();

        TableUtils.initilizeCheckColumn(checkedColumnWypozyczenia);
        List<TableColumn<Wypozyczenie, ?>> columns = new ArrayList<>(wypozyczeniaTable.getColumns());
        columns.remove(0);
        String[] attribNames = {"id_wypozyczenia", "data_wypozyczenia","data_zwrotu","klient_nr_dowodu",
                "id_prac", "id_roweru", "id_akcesoria", "aktualnaCena"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        TableUtils.fillTable(wypozyczeniaTable, wypozyczenieDao);
        wypozyczeniaTable.setPlaceholder(new Label("Brak wypożyczeń"));
    }

    private void initializeKlienci(){
        klientDao = new KlientDao();
        TableUtils.initilizeCheckColumn(checkedColumnKlienci);
        List<TableColumn<Klient, ?>> columns = new ArrayList<>(klienciTable.getColumns());
        columns.remove(0);
        String[] attribNames = {"nr_dowodu", "imie","nazwisko", "nr_telefonu"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        TableUtils.fillTable(klienciTable, klientDao);
        klienciTable.setPlaceholder(new Label("Brak klientów"));
    }

    private void initializeRowery(){
        rowerDao = new RowerDao();

        TableUtils.initilizeCheckColumn(checkedColumnRowery);
        List<TableColumn<Rower, ?>> columns = new ArrayList<>(roweryTable.getColumns());
        columns.remove(0);
        String[] attribNames = {"id", "model", "status", "kolor", "dataZakupu"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        TableUtils.fillTable(roweryTable, rowerDao);
        roweryTable.setPlaceholder(new Label("Brak rowerów"));
    }

    private void initializeAkcesoria(){
        akcesoriumDao = new AkcesoriumDao();

        TableUtils.initilizeCheckColumn(checkedColumnAkcesoria);
        List<TableColumn<Akcesorium, ?>> columns = new ArrayList<>(akcesoriaTable.getColumns());
        columns.remove(0);
        String[] attribNames = {"id", "rodzaj", "status", "dataZakupu"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        TableUtils.fillTable(akcesoriaTable, akcesoriumDao);
        akcesoriaTable.setPlaceholder(new Label("Brak akcesoriów"));
    }

    private void initializePracownicy(){
        pracownikWypozyczalniDao = new PracownikWypozyczalniDao();

        TableUtils.initilizeCheckColumn(checkedColumnPracownicy);
        List<TableColumn<PracownikWypozyczalni, ?>> columns = new ArrayList<>(pracownicyTable.getColumns());
        columns.remove(0);
        String[] attribNames = {"id", "imie", "nazwisko", "dataZatrudnienia", "dataZwolnienia"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        TableUtils.fillTable(pracownicyTable, pracownikWypozyczalniDao);
        pracownicyTable.setPlaceholder(new Label("Brak pracowników"));
    }

    private void initializeModeleRowerow(){
        modelRoweruDao = new ModelRoweruDao();

        TableUtils.initilizeCheckColumn(checkedColumnModele);
        List<TableColumn<ModelRoweru, ?>> columns = new ArrayList<>(modeleRowerowTable.getColumns());
        columns.remove(0);
        String[] attribNames = {"nazwa", "typ", "rozmiar", "cenaZaDzien", "cenaZaMiesiac", "opis", "wolne"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        TableUtils.fillTable(modeleRowerowTable, modelRoweruDao);
        modeleRowerowTable.setPlaceholder(new Label("Brak modeli rowerów"));
    }

    private void initializeRodzajeAkcesorium(){
        rodzajAkcesoriumDao = new RodzajAkcesoriumDao();

        TableUtils.initilizeCheckColumn(checkedColumnRodzaje);
        List<TableColumn<RodzajAkcesorium, ?>> columns = new ArrayList<>(rodzajAkcesoriumTable.getColumns());
        columns.remove(0);
        String[] attribNames = {"nazwa", "cenaZaDzien", "cenaZaMiesiac", "kaucja", "wolne"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        TableUtils.fillTable(rodzajAkcesoriumTable, rodzajAkcesoriumDao);
        rodzajAkcesoriumTable.setPlaceholder(new Label("Brak rodzajów akcesoriów"));
    }

    private void initializeSubskrybcje(){
        subskrybcjaDao = new SubskrybcjaDao();

        TableUtils.initilizeCheckColumn(checkedColumnSubskrybcje);
        List<TableColumn<Subskrybcja, ?>> columns = new ArrayList<>(subskrybcjeTable.getColumns());
        columns.remove(0);
        String[] attribNames = {"klientNrDowodu", "idRoweru", "idAkcesorium", "idPrac",
                "dataRozpoczecia", "dataZakonczenia", "cenaZaMiesiac"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        TableUtils.fillTable(subskrybcjeTable, subskrybcjaDao);
        subskrybcjeTable.setPlaceholder(new Label("Brak subskrybcji"));
    }

    private void initializeButtonWypozyczRower(){
        wypozyczRower.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Rower chosenItem = ButtonUtils.getSingleChosenItem(roweryTable,
                        "Wybierz jeden rower, aby go wypożyczyć.");
                if(chosenItem != null){
                    try {
                        Parent root;
                        NewWypozyczenieController.enterRowerMode("" + chosenItem.getId(), chosenItem.getModel());
                        root = FXMLLoader.load(getClass().getResource("/fxml/newWypozyczenie.fxml"));
                        Stage stage = StageUtils.createNewStage(root, "Wypożycz Rower");
                        stage.setResizable(false);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(wypozyczRower.getScene().getWindow());
                        stage.showAndWait();
                        NewWypozyczenieController.exitRowerMode();
                        TableUtils.refreshTable(wypozyczeniaTable, wypozyczenieDao);
                        TableUtils.refreshTable(roweryTable, rowerDao);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

        });
    }

    private void initializeButtonWypozyczModel(){
        wypozyczModel.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                ModelRoweru chosenItem = ButtonUtils.getSingleChosenItem(modeleRowerowTable,
                        "Wybierz jeden rower, aby go wypożyczyć.");
                if(chosenItem != null){
                    try {
                        List<Rower> wolneRowery = rowerDao.get(new Rower(-1, null, null,
                                chosenItem.getNazwa(),"wolny"));
                        if(wolneRowery.size() > 0){
                            Rower chosenRower = wolneRowery.get(0);
                            Parent root;
                            NewWypozyczenieController.enterRowerMode("" + chosenRower.getId(),
                                    chosenRower.getModel());
                            root = FXMLLoader.load(getClass().getResource("/fxml/newWypozyczenie.fxml"));
                            Stage stage = StageUtils.createNewStage(root, "Wypożycz Rower");
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initOwner(wypozyczModel.getScene().getWindow());
                            stage.showAndWait();
                            NewWypozyczenieController.exitRowerMode();
                            TableUtils.refreshTable(modeleRowerowTable, modelRoweruDao);
                            TableUtils.refreshTable(wypozyczeniaTable, wypozyczenieDao);
                            TableUtils.refreshTable(roweryTable, rowerDao);
                        }
                        else{
                            AlertsUtil.showErrorAlert("Brak wolnego roweru", "Błąd",
                                    "Niestety, nie ma wolnego roweru dla wybranego modelu.\n" +
                                            "Wybierz inny model, aby go wypożyczyć");
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

        });
    }

    private void initializeButtonPrzeglady(){
        przeglady.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Rower chosenItem = ButtonUtils.getSingleChosenItem(roweryTable,
                        "Wybierz jeden rower, aby wyświetlić jego przeglądy.");
                if(chosenItem != null){
                    try {
                        Parent root;
                        PrzegladyController.provideRowerId(chosenItem.getId());
                        root = FXMLLoader.load(getClass().getResource("/fxml/przeglady.fxml"));
                        Stage stage = StageUtils.createNewStage(root, "Przeglądy");
                        stage.setResizable(false);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(przeglady.getScene().getWindow());
                        stage.showAndWait();
                        TableUtils.refreshTable(roweryTable, rowerDao);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

        });
    }

    private void initializeButtonUsterki(){
        usterki.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Rower chosenItem = ButtonUtils.getSingleChosenItem(roweryTable,
                        "Wybierz jeden rower, aby wyświetlić jego usterki.");
                if(chosenItem != null){
                    try {
                        Parent root;
                        UsterkiController.provideRowerId(chosenItem.getId());
                        root = FXMLLoader.load(getClass().getResource("/fxml/usterki.fxml"));
                        Stage stage = StageUtils.createNewStage(root, "Usterki");
                        stage.setResizable(false);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(usterki.getScene().getWindow());
                        stage.showAndWait();
                        TableUtils.refreshTable(roweryTable, rowerDao);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

        });
    }

    private void initializeButtonZwrocWypozyczenie(){
        zwrocWypozyczenia.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                ObservableList<Wypozyczenie> items = wypozyczeniaTable.getItems();
                for(Wypozyczenie item : items){
                    if(item.ifChecked()){
                        wypozyczenieDao.update(new Wypozyczenie(item.getId_wypozyczenia(), item.getData_wypozyczenia(),
                                Date.valueOf(LocalDate.now()), item.getKlient_nr_dowodu(),
                                item.getId_prac(), item.getId_roweru(), item.getId_akcesoria()), item);
                        TableUtils.refreshTable(wypozyczeniaTable, wypozyczenieDao);
                    }
                }
            }

        });
    }

    private void initializeButtonZakonczSubskrybcje(){
        zakonczSubskrybcje.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                ObservableList<Subskrybcja> items = subskrybcjeTable.getItems();
                for(Subskrybcja item : items){
                    if(item.ifChecked()){
                        subskrybcjaDao.update(new Subskrybcja(item.getDataRozpoczecia(), item.getKlientNrDowodu(),
                                item.getIdPrac(), item.getIdRoweru(), item.getIdAkcesorium(),
                                        Date.valueOf(LocalDate.now())), item);
                        TableUtils.refreshTable(subskrybcjeTable, subskrybcjaDao);
                    }
                }
            }

        });
    }

    private void initializeCheckboxes(){
        aktywneWypozyczenia.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                WypozyczenieDao.setActiveOnly(newValue);
                TableUtils.refreshTable(wypozyczeniaTable, wypozyczenieDao);
            }
        });

        aktywneSubskrybcje.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                SubskrybcjaDao.setActiveOnly(newValue);
                TableUtils.refreshTable(subskrybcjeTable, subskrybcjaDao);
            }
        });

    }


    private void initializeButtons(){
        ButtonUtils.initializeButtonLaunch(nowyKlient, "/fxml/newKlient.fxml", "Nowy Klient", klienciTable, klientDao, TableUtils.modeREFRESH);
        ButtonUtils.initializeButtonLaunch(nowyPracownik, "/fxml/newPracownik.fxml", "Nowy Pracownik",
                pracownicyTable, pracownikWypozyczalniDao, TableUtils.modeREFRESH);
        ButtonUtils.initializeButtonLaunch(nowyRower, "/fxml/newRower.fxml", "Nowy Rower", roweryTable, rowerDao, TableUtils.modeREFRESH);
        ButtonUtils.initializeButtonLaunch(noweAkcesorium, "/fxml/newAkcesorium.fxml", "Nowe Akcesorium",
                akcesoriaTable, akcesoriumDao, TableUtils.modeREFRESH);
        ButtonUtils.initializeButtonLaunch(noweWypozyczenie, "/fxml/newWypozyczenie.fxml", "Nowe Wypożyczenie",
                wypozyczeniaTable, wypozyczenieDao, TableUtils.modeREFRESH);
        ButtonUtils.initializeButtonLaunch(nowyModel, "/fxml/newModel.fxml", "Nowy Model", modeleRowerowTable, modelRoweruDao, TableUtils.modeREFRESH);
        ButtonUtils.initializeButtonLaunch(nowyRodzaj, "/fxml/newRodzaj.fxml", "Nowy Rodzaj",
                rodzajAkcesoriumTable, rodzajAkcesoriumDao, TableUtils.modeREFRESH);
        ButtonUtils.initializeButtonLaunch(nowaSubskrybcja, "/fxml/newSubskrybcja.fxml", "Nowa Subskrybcja",
                subskrybcjeTable, subskrybcjaDao, TableUtils.modeREFRESH);

        initializeButtonPrzeglady();
        initializeButtonUsterki();

        ButtonUtils.initializeButtonRefresh(refreshWypozyczenia, wypozyczeniaTable, wypozyczenieDao);
        ButtonUtils.initializeButtonRefresh(refreshKlienci, klienciTable, klientDao);
        ButtonUtils.initializeButtonRefresh(refreshRowery, roweryTable, rowerDao);
        ButtonUtils.initializeButtonRefresh(refreshAkcesoria, akcesoriaTable, akcesoriumDao);
        ButtonUtils.initializeButtonRefresh(refreshPracownicy, pracownicyTable, pracownikWypozyczalniDao);
        ButtonUtils.initializeButtonRefresh(refreshModele, modeleRowerowTable, modelRoweruDao);
        ButtonUtils.initializeButtonRefresh(refreshRodzaje, rodzajAkcesoriumTable, rodzajAkcesoriumDao);
        ButtonUtils.initializeButtonRefresh(refreshSubskrybcje, subskrybcjeTable, subskrybcjaDao);

        ButtonUtils.initializeButtonDelete(usunWypozyczenia, wypozyczeniaTable, wypozyczenieDao);
        ButtonUtils.initializeButtonDelete(usunKlienci, klienciTable, klientDao);
        ButtonUtils.initializeButtonDelete(usunRowery, roweryTable, rowerDao);
        ButtonUtils.initializeButtonDelete(usunAkcesoria, akcesoriaTable, akcesoriumDao);
        ButtonUtils.initializeButtonDelete(usunPracownicy, pracownicyTable, pracownikWypozyczalniDao);
        ButtonUtils.initializeButtonDelete(usunModele, modeleRowerowTable, modelRoweruDao);
        ButtonUtils.initializeButtonDelete(usunRodzaje, rodzajAkcesoriumTable, rodzajAkcesoriumDao);
        ButtonUtils.initializeButtonDelete(usunSubskrybcje, subskrybcjeTable, subskrybcjaDao);

        ButtonUtils.initializeButtonModify(modyfikujWypozyczenie, wypozyczeniaTable,
                wypozyczenieDao, "/fxml/modifyWypozyczenie.fxml",
                "Wybierz jedno wypożyczenie, aby je zmodyfikować jego dane.");
        ButtonUtils.initializeButtonModify(modyfikujKlient, klienciTable, klientDao, "/fxml/modifyKlient.fxml",
                "Wybierz jednego klienta, aby zmodyfikować jego dane.");
        ButtonUtils.initializeButtonModify(modyfikujPracownik, pracownicyTable, pracownikWypozyczalniDao,
                "/fxml/modifyPracownik.fxml", "Wybierz jednego pracownika, aby zmodyfikować jego dane.");
        ButtonUtils.initializeButtonModify(modyfikujRower, roweryTable, rowerDao, "/fxml/modifyRower.fxml",
                "Wybierz jeden rower, aby zmodyfikować jego dane.");
        ButtonUtils.initializeButtonModify(modyfikujModel, modeleRowerowTable, modelRoweruDao, "/fxml/modifyModel.fxml",
        "Wybierz jeden model, aby zmodyfikować jego dane.");
        ButtonUtils.initializeButtonModify(modyfikujAkcesorium, akcesoriaTable, akcesoriumDao,
                "/fxml/modifyAkcesorium.fxml", "Wybierz jedno akcesorium, aby zmodyfikować jego dane");
        ButtonUtils.initializeButtonModify(modyfikujRodzaj, rodzajAkcesoriumTable, rodzajAkcesoriumDao,
                "/fxml/modifyRodzaj.fxml", "Wybierz jeden rodzaj, aby zmodyfikować jego dane.");
        ButtonUtils.initializeButtonModify(modyfikujSubskrybcja, subskrybcjeTable, subskrybcjaDao,
                "/fxml/modifySubskrybcja.fxml", "Wybierz jedną subskrybcję, aby zmodyfikować jej dane.");

        ButtonUtils.initializeButtonLaunch(wyszukajWypozyczenia, "/fxml/searchWypozyczenia.fxml", "Wyszukaj",
                wypozyczeniaTable, wypozyczenieDao, TableUtils.modeSEARCHRESULT);
        ButtonUtils.initializeButtonLaunch(wyszukajKlienci, "/fxml/searchKlienci.fxml", "Wyszukaj",
                klienciTable, klientDao, TableUtils.modeSEARCHRESULT);
        ButtonUtils.initializeButtonLaunch(wyszukajModele, "/fxml/searchModele.fxml", "Wyszukaj",
                modeleRowerowTable, modelRoweruDao, TableUtils.modeSEARCHRESULT);
        ButtonUtils.initializeButtonLaunch(wyszukajRowery, "/fxml/searchRowery.fxml", "Wyszukaj",
                roweryTable, rowerDao, TableUtils.modeSEARCHRESULT);
        ButtonUtils.initializeButtonLaunch(wyszukajRodzaje, "/fxml/searchRodzaje.fxml", "Wyszukaj",
                rodzajAkcesoriumTable, rodzajAkcesoriumDao, TableUtils.modeSEARCHRESULT);
        ButtonUtils.initializeButtonLaunch(wyszukajAkcesoria, "/fxml/searchAkcesoria.fxml", "Wyszukaj",
                akcesoriaTable, akcesoriumDao, TableUtils.modeSEARCHRESULT);
        ButtonUtils.initializeButtonLaunch(wyszukajPracownicy, "/fxml/searchPracownicy.fxml", "Wyszukaj",
                pracownicyTable, pracownikWypozyczalniDao, TableUtils.modeSEARCHRESULT);
        ButtonUtils.initializeButtonLaunch(wyszukajSubskrybcje, "/fxml/searchSubskrybcje.fxml", "Wyszukaj",
                subskrybcjeTable, subskrybcjaDao, TableUtils.modeSEARCHRESULT);

        initializeButtonZwrocWypozyczenie();
        initializeButtonZakonczSubskrybcje();
        initializeButtonWypozyczRower();
        initializeButtonWypozyczModel();

        initializeCheckboxes();
    }

}
