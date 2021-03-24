package gui.controllers;

import database.daos.UsterkaDao;
import database.objects.Modifiable;
import database.objects.Usterka;
import gui.controllers.insertion.NewUsterkaController;
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

public class UsterkiController extends SimpleControlsTable<Usterka> {

    private static long RowerId;

    private long rowerId;

    public UsterkiController(){
        super();
        this.rowerId = RowerId;
    }

    @Override
    protected void initializeTable() {
        dao = new UsterkaDao();
        List<TableColumn<Usterka, ?>> columns = new ArrayList<>(table.getColumns());
        TableUtils.initilizeCheckColumn((TableColumn<Usterka, Boolean>) columns.get(0));
        columns.remove(0);
        String[] attribNames = {"dataZgloszenia", "dataNaprawy", "pracownikWpisujacy", "pracownikNaprawiajacy", "opis"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        initializeButtons();
        table.setItems(FXCollections.observableList(dao.get(new Usterka(null, null,
                -1, rowerId, null, -1))));
        table.setEditable(true);
    }

    @Override
    protected void initalizeAddAndModifyButtons() {
        initializeButtonDodaj("/fxml/newUsterka.fxml", "Nowa Usterka");
        initializeButtonModify();
    }

    private void initializeButtonModify(){
        modyfikuj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                Usterka chosenItem = ButtonUtils.getSingleChosenItem(table,
                        "Wybierz jedną usterkę, aby zmodyfikować jej dane.");
                if(chosenItem != null){
                    try {
                        Parent root;
                        ((Modifiable) chosenItem).setOrigin();
                        root = FXMLLoader.load(getClass().getResource("/fxml/modifyUsterka.fxml"));
                        Stage stage = StageUtils.createNewStage(root, "Modyfikacja danych");
                        stage.setResizable(false);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.initOwner(modyfikuj.getScene().getWindow());
                        stage.showAndWait();
                        table.setItems(FXCollections.observableList(dao.get(new Usterka(null, null,
                                -1, rowerId, null, -1))));
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
                    NewUsterkaController.provideRowerId(rowerId);
                    root = FXMLLoader.load(getClass().getResource(resource));
                    Stage stage = new Stage();
                    stage.setTitle(title);
                    stage.setScene(new Scene(root));
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initOwner(dodaj.getScene().getWindow());
                    stage.showAndWait();
                    table.setItems(FXCollections.observableList(dao.get(new Usterka(null, null,
                            -1, rowerId, null, -1))));
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
                table.setItems(FXCollections.observableList(dao.get(new Usterka(null, null,
                        -1, rowerId, null, -1))));
            }

        });
    }

    @Override
    protected void initializeButtonWyszukaj() {
        ButtonUtils.initializeButtonLaunch(wyszukaj, "/fxml/searchUsterki.fxml", "Wyszukaj", table, dao,
                TableUtils.modeSEARCHRESULT);
    }

    public static void provideRowerId(long id){
        RowerId = id;
    }

}
