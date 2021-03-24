package gui.controllers;

import database.objects.Modifiable;
import database.objects.Przeglad;
import database.daos.PrzegladDao;
import gui.controllers.insertion.NewPrzegladController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.ButtonUtils;
import utils.StageUtils;
import utils.TableUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrzegladyController extends SimpleControlsTable<Przeglad> {

    private static long RowerId;

    private long rowerId;

    public PrzegladyController(){
        this.rowerId = RowerId;
    }


    @Override
    protected void initializeTable() {
        dao = new PrzegladDao();
        List<TableColumn<Przeglad, ?>> columns = new ArrayList<>(table.getColumns());
        TableUtils.initilizeCheckColumn((TableColumn<Przeglad, Boolean>) columns.get(0));
        columns.remove(0);
        String[] attribNames = {"dataWykonania", "pracownik", "opis"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        initializeButtons();
        table.setItems(FXCollections.observableList(dao.get(new Przeglad(null, null,
                rowerId, -1))));
        table.setEditable(true);
    }

    @Override
    protected void initalizeAddAndModifyButtons() {
        initializeButtonDodaj("/fxml/newPrzeglad.fxml", "Nowy Przegląd");
        initializeButtonModify();
    }

    private void initializeButtonModify(){
        modyfikuj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Przeglad chosenItem = ButtonUtils.getSingleChosenItem(table,
                        "Wybierz jeden przegląd, aby zmodyfikować jego dane.");
                if(chosenItem != null){
                    try {
                        Parent root;
                        ((Modifiable) chosenItem).setOrigin();
                        root = FXMLLoader.load(getClass().getResource("/fxml/modifyPrzeglad.fxml"));
                        Stage stage = StageUtils.createNewStage(root, "Modyfikacja danych");
                        stage.setResizable(false);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(modyfikuj.getScene().getWindow());
                        stage.showAndWait();
                        table.setItems(FXCollections.observableList(dao.get(new Przeglad(null, null,
                                rowerId, -1))));
                        ((Modifiable) chosenItem).resetOrigin();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

        });
    }

    private void initializeButtonDodaj(String resource, String title){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Parent root;
                try {
                    NewPrzegladController.provideRowerId(rowerId);
                    root = FXMLLoader.load(getClass().getResource(resource));
                    Stage stage = new Stage();
                    stage.setTitle(title);
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(dodaj.getScene().getWindow());
                    stage.showAndWait();
                    if(table != null && dao !=null) table.setItems(FXCollections.observableList(dao.get(
                            new Przeglad(null, null, rowerId, -1))));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        });
    }

    @Override
    protected void initializeButtonRefresh(){
        refresh.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                table.setItems(FXCollections.observableList(dao.get(new Przeglad(null, null,
                        rowerId, -1))));
            }

        });
    }

    @Override
    protected void initializeButtonWyszukaj() {
        ButtonUtils.initializeButtonLaunch(wyszukaj, "/fxml/searchPrzeglady.fxml", "Wyszukaj", table, dao,
                TableUtils.modeSEARCHRESULT);
    }

    public static void provideRowerId(long id){
        RowerId = id;
    }
}
