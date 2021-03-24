package gui.controllers.choiceTables;

import database.objects.Akcesorium;
import database.daos.AkcesoriumDao;
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
 * controller class for chooseAkcesoriumID.fxml,
 * controls window responsible for choosing one of elements of type Akcesorium (that is available for rent)
 */
public class ChooseAkcesoriumController extends ChoiceTable<Akcesorium> {

    private static final String EmptyText = "Brak wolnych akcesoriów";

    private static boolean ifWolneOnly = true;

    @Override
    protected void activateTableRowsOnClickFunc(){
        table.setRowFactory(tv -> {
            TableRow<Akcesorium> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Akcesorium rowData = row.getItem();
                    DummyValuesPasser.setLongValue(rowData.getId());
                    DummyValuesPasser.setStringValue(rowData.getRodzaj());
                    Stage stage = (Stage) table.getScene().getWindow();
                    stage.close();
                }
            });
            return row ;
        });
    }

    @Override
    protected void initializeTable() {
        dao = new AkcesoriumDao();
        List<TableColumn<Akcesorium, ?>> columns = new ArrayList<>(table.getColumns());
        String[] attribNames = {"id", "rodzaj", "status", "dataZakupu"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        activateTableRowsOnClickFunc();
        if(ifWolneOnly) table.setItems(FXCollections.observableList(dao.get(new Akcesorium(-1, null, null,
                "wolne"))));
        else TableUtils.fillTable(table, dao);
        table.setEditable(true);
        table.setPlaceholder(new Label(EmptyText));
    }

    public static void setIfWolneOnly(boolean ifWolne){
        ifWolneOnly = ifWolne;
    }
}
