package gui.controllers;

import database.daos.Dao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import utils.TableUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * parent class for fxml controllers which present one TableView containing T type elements
 * @param <T> represents class of elements in the TableView
 */
public abstract class BasicTable<T> implements Initializable {
    protected Dao<T> dao;

    @FXML protected TableView<T> table = new TableView<>();

    protected void initializeTable(){
        TableUtils.fillTable(table, dao);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }
}
