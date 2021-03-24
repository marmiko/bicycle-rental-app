package gui.controllers.choiceTables;

import database.objects.Rower;
import database.daos.RowerDao;
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
 * controller class for chooseRowerID.fxml,
 * controls window responsible for choosing one of elements of type Rower (that are available for rent)
 */
public class ChooseRowerController extends ChoiceTable<Rower> {

    private static final String EmptyText = "Brak wolnych rowerów";

    private static boolean ifWolneOnly = true;

    @Override
    protected void activateTableRowsOnClickFunc(){
        table.setRowFactory(tv -> {
            TableRow<Rower> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Rower rowData = row.getItem();
                    DummyValuesPasser.setLongValue(rowData.getId());
                    DummyValuesPasser.setStringValue(rowData.getModel());
                    Stage stage = (Stage) table.getScene().getWindow();
                    stage.close();
                }
            });
            return row ;
        });
    }

    @Override
    protected void initializeTable(){
        dao = new RowerDao();
        List<TableColumn<Rower, ?>> columns = new ArrayList<>(table.getColumns());
        String[] attribNames = {"id", "model", "status", "kolor", "dataZakupu"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        activateTableRowsOnClickFunc();
        if(ifWolneOnly) table.setItems(FXCollections.observableList(dao.get(new Rower(-1, null, null,
                null, "wolny"))));
        else TableUtils.fillTable(table, dao);
        table.setEditable(true);
        table.setPlaceholder(new Label(EmptyText));
    }

    public static void setIfWolneOnly(boolean ifWolne){
        ifWolneOnly = ifWolne;
    }

}
