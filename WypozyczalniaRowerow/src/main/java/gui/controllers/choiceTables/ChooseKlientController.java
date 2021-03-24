package gui.controllers.choiceTables;

import database.objects.Klient;
import database.daos.KlientDao;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import utils.TableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * controller class for chooseKlient.fxml,
 * controls window responsible for choosing one of elements of type Klient
 */
public class ChooseKlientController extends ChoiceTable<Klient> {

    private static final String EmptyText = "Brak klientów";

    @Override
    protected void initializeTable() {
        dao = new KlientDao();
        List<TableColumn<Klient, ?>> columns = new ArrayList<>(table.getColumns());
        String[] attribNames = {"nr_dowodu", "imie","nazwisko", "nr_telefonu"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        table.setPlaceholder(new Label(EmptyText));
        super.initializeTable();
    }
}
