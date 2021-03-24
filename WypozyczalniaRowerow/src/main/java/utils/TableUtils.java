package utils;

import database.objects.Checkable;
import database.daos.Dao;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * utility class used to perform operations on JavaFX TableView and TableColumns
 */
public class TableUtils {

    public static final int modeNONE = 0;
    public static final int modeREFRESH = 1;
    public static final int modeSEARCHRESULT = 2;

    /**
     * initializes CheckBoxColumn
     *
     * @param column TableColumn to be initialised
     * @param <T>    class of objects contained in the TableView to which TableColumn is assigned,
     *               it shall be a class implementing Checkable interface
     */
    public static <T> void initilizeCheckColumn(TableColumn<T, Boolean> column) {
        column.setCellValueFactory(cellData -> ((Checkable) cellData.getValue()).getIfChecked());
        column.setCellFactory(tc -> new CheckBoxTableCell<>());
    }

    /**
     * initializes TableColumns with class attrigutes
     *
     * @param columns     List of TableColumns to be initialized
     * @param attribNames names of the class T attributes, that their values are to be assigned to TableColumns
     * @param <T>         class of objects contained in the TableView to which TableColumn is assigned
     */
    public static <T> void initializeAttribColumns(List<TableColumn<T, ?>> columns, String[] attribNames) {
        if (columns.size() == attribNames.length) {
            for (int i = columns.size() - 1; i >= 0; i--) {
                columns.get(i).setCellValueFactory(new PropertyValueFactory<>(attribNames[i]));
            }
        } else {
            AlertsUtil.showErrorAlert(null, "Błąd inicializacji kolumn.",
                    "Niezgodność liczby kolumn z iością podanych atrybutów.");
        }
    }

    /**
     * fills provided TableView with values
     *
     * @param table TableView to be filled
     * @param dao   instance of the Dao of the class T, which provides T elements to be put into table
     * @param <T>   class of objects contained in the TableView
     */
    public static <T> void fillTable(TableView<T> table, Dao<T> dao) {
        table.setItems(FXCollections.observableList(dao.getAll()));
        table.setEditable(true);
    }

    /**
     * refreshes contents of the TableView
     *
     * @param table TableView to be refreshed
     * @param dao   Dao providing data for refresh
     * @param <T>   class of elements stored in the table
     */
    public static <T> void refreshTable(TableView<T> table, Dao<T> dao) {
        table.setItems(FXCollections.observableList(dao.getAll()));
    }

}
