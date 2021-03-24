package utils;

import database.objects.Checkable;
import database.daos.Dao;
import database.objects.Modifiable;
import gui.controllers.search.SearchModeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ButtonUtils {


    /**
     * initializes button responsible for launching window with form that updates database
     *
     * @param btn
     * @param resource
     * @param title
     * @param table
     * @param dao
     * @param <T>
     */
    public static <T> void initializeButtonLaunch(Button btn, String resource, String title,
                                                  TableView<T> table, Dao<T> dao, int mode) {
        btn.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource(resource));
                    Stage stage = new Stage();
                    stage.setTitle(title);
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(btn.getScene().getWindow());
                    stage.showAndWait();
                    if (mode != TableUtils.modeNONE && table != null && dao != null){
                        if(mode == TableUtils.modeREFRESH) TableUtils.refreshTable(table, dao);
                        else if (mode == TableUtils.modeSEARCHRESULT){
                            SearchModeController.exitSearchMode();
                            if(dao.ifSerchParamsReady()){
                                table.setItems(FXCollections.observableList(dao.getWithPresetParams()));
                            }
                        }
                    }

                } catch (IOException ioException) {
                    AlertsUtil.showErrorAlert(null, "Błąd otwarcia okna",
                            "Otwieranie nowego okna nie powiodło się.");
                }
            }

        });
    }

    public static <T> void initializeButtonDelete(Button btn, TableView<T> table, Dao<T> dao) {
        btn.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                ObservableList<T> items = table.getItems();
                for (T item : items) {
                    Checkable check = (Checkable) item;
                    if (check.ifChecked()) {
                        dao.delete(item);
                        TableUtils.refreshTable(table, dao);
                    }
                }
            }

        });
    }

    public static <T> void initializeButtonRefresh(Button btn, TableView<T> table, Dao<T> dao) {
        btn.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                TableUtils.refreshTable(table, dao);
            }

        });
    }

    public static <T> void initializeButtonWybierz(Button btn, String fxml, TextField field) {
        btn.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource(fxml));
                    Stage stage = new Stage();
                    stage.setTitle("Wybierz");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(btn.getScene().getWindow());
                    stage.showAndWait();
                    String temp = DummyValuesPasser.getStringValue();
                    if (temp != null) field.setText(temp);
                    DummyValuesPasser.setStringValue(null);
                } catch (IOException ioException) {
                    AlertsUtil.showErrorAlert(null, "Błąd otwarcia okna",
                            "Otwieranie nowego okna nie powiodło się.");
                }
            }
        });
    }

    public static <T> void initializeButtonWybierz(Button btn, String fxml, TextField fieldID, TextField fieldName) {
        btn.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Parent root;
                try {
                    root = FXMLLoader.load(getClass().getResource(fxml));
                    Stage stage = new Stage();
                    stage.setTitle("Wybierz");
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(btn.getScene().getWindow());
                    stage.showAndWait();
                    String tempStr = DummyValuesPasser.getStringValue();
                    if (tempStr != null) fieldName.setText(tempStr);
                    DummyValuesPasser.setStringValue(null);
                    long tempL = DummyValuesPasser.getLongValue();
                    if (tempL != -1) fieldID.setText("" + tempL);
                    DummyValuesPasser.setLongValue(-1);
                } catch (IOException ioException) {
                    AlertsUtil.showErrorAlert(null, "Błąd otwarcia okna",
                            "Otwieranie nowego okna nie powiodło się.");
                }
            }
        });
    }

    public static <T> T getSingleChosenItem(TableView<T> table, String errorMessage) {
        int count = 0;
        ObservableList<T> items = table.getItems();
        T chosenItem = null;
        for (T item : items) {
            Checkable itemCheck = (Checkable) item;
            if (itemCheck.ifChecked()) {
                chosenItem = item;
                count++;
                if (count > 1) {
                    break;
                }
            }
        }
        if (count > 1 || count == 0) {
            AlertsUtil.showErrorAlert("Nieprawidłowa liczba obiektów", "Błąd",
                    errorMessage);
            return null;
        } else return chosenItem;
    }

    public static <T> void initializeButtonModify(Button btn, TableView<T> table, Dao<T> dao, String fxml,
                                                  String errorMessage){
        btn.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                T chosenItem = getSingleChosenItem(table, errorMessage);
                if(chosenItem != null){
                    try {
                        Parent root;
                        ((Modifiable) chosenItem).setOrigin();
                        root = FXMLLoader.load(getClass().getResource(fxml));
                        Stage stage = StageUtils.createNewStage(root, "Modyfikacja danych");
                        stage.setResizable(false);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(btn.getScene().getWindow());
                        stage.showAndWait();
                        TableUtils.refreshTable(table, dao);
                        ((Modifiable) chosenItem).resetOrigin();
                    } catch (IOException ioException) {
                        AlertsUtil.showErrorAlert(null, "Błąd otwarcia okna",
                                "Otwieranie nowego okna nie powiodło się.");
                    }
                }
            }

        });
    }

}
