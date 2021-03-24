package gui.controllers.choiceTables;

import database.daos.AkcesoriumDao;
import database.daos.RodzajAkcesoriumDao;
import database.objects.Akcesorium;
import database.objects.RodzajAkcesorium;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;
import utils.DummyValuesPasser;
import utils.TableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * controller class for chooseAkcesoriumNazwa.fxml,
 * controls window responsible for choosing one of elements of type RodzajAkcesorium (that describe at least one object
 * of type Akcesorium, that is available for rent)
 */
public class ChooseRodzajController extends ChoiceTable<RodzajAkcesorium> {

    private static final String EmptyText = "Brak wolnych akcesoriów";

    @Override
    protected void activateTableRowsOnClickFunc(){
        table.setRowFactory(tv -> {
            TableRow<RodzajAkcesorium> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    RodzajAkcesorium rowData = row.getItem();
                    List<Akcesorium> temp = (new AkcesoriumDao()).get(new Akcesorium(-1, null,
                            dao.getKeyValue(rowData), null));
                    DummyValuesPasser.setLongValue(temp.get(0).getId());
                    DummyValuesPasser.setStringValue(temp.get(0).getRodzaj());
                    Stage stage = (Stage) table.getScene().getWindow();
                    stage.close();
                }
            });
            return row ;
        });
    }

    @Override
    protected void initializeTable() {
        dao = new RodzajAkcesoriumDao();
        List<TableColumn<RodzajAkcesorium, ?>> columns = new ArrayList<>(table.getColumns());
        String[] attribNames = {"nazwa", "cenaZaDzien", "cenaZaMiesiac", "kaucja", "wolne"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        activateTableRowsOnClickFunc();
        String[] params = {"nazwa in (select rodzaj_akcesorium_nazwa from AKCESORIA where status = 'wolne')"};
        table.setItems(FXCollections.observableList(((RodzajAkcesoriumDao) dao).get(params)));
        table.setEditable(true);
        table.setPlaceholder(new Label(EmptyText));
    }
}
