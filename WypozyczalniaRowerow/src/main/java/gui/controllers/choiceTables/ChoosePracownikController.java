package gui.controllers.choiceTables;

import database.objects.PracownikWypozyczalni;
import database.daos.PracownikWypozyczalniDao;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import utils.TableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * controller class for choosePracownik.fxml,
 * controls window responsible for choosing one of elements of type Pracownik
 */
public class ChoosePracownikController extends ChoiceTable<PracownikWypozyczalni> {

    private static final String EmptyText = "Brak pracowników";

    @Override
    protected void initializeTable() {
        dao = new PracownikWypozyczalniDao();
        List<TableColumn<PracownikWypozyczalni, ?>> columns = new ArrayList<>(table.getColumns());
        String[] attribNames = {"id", "imie", "nazwisko", "dataZatrudnienia", "dataZwolnienia"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        super.initializeTable();
        table.setPlaceholder(new Label(EmptyText));
    }
}
