package gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.ButtonUtils;

/**
 * parent class for fxml controllers which provide simple operations (Add, Modify, Delete) on the presented TableView
 * @param <T> represents class of elements in the TableView
 */
public abstract class SimpleControlsTable<T> extends BasicTable<T> {

    @FXML protected Button dodaj = new Button();
    @FXML protected Button usun = new Button();
    @FXML protected Button modyfikuj = new Button();
    @FXML protected Button refresh = new Button();
    @FXML protected Button wyszukaj = new Button();

    protected abstract void initalizeAddAndModifyButtons();

    protected void initializeButtons(){
        ButtonUtils.initializeButtonDelete(usun, table, dao);
        initializeButtonRefresh();
        initalizeAddAndModifyButtons();
        initializeButtonWyszukaj();
    }

    protected void initializeButtonRefresh(){
        ButtonUtils.initializeButtonRefresh(refresh, table, dao);
    }

    @Override
    protected void initializeTable() {
        initializeButtons();
        super.initializeTable();
    }

    protected void initializeButtonWyszukaj(){}
}
