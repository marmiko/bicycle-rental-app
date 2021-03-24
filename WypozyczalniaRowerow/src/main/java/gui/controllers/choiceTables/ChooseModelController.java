package gui.controllers.choiceTables;

import database.objects.ModelRoweru;
import database.daos.ModelRoweruDao;
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
 * controller class for chooseRowerNazwa.fxml,
 * controls window responsible for choosing one of elements of type ModelRoweru (that describe at least one object
 * of type Rower, that is available for rent)
 */
public class ChooseModelController extends ChoiceTable<ModelRoweru> {

    private static final String EmptyText = "Brak wolnych rowerów";

    @Override
    protected void activateTableRowsOnClickFunc(){
        table.setRowFactory(tv -> {
            TableRow<ModelRoweru> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    ModelRoweru rowData = row.getItem();
                    List<Rower> temp = (new RowerDao()).get(new Rower(-1, null, null,
                            dao.getKeyValue(rowData), "wolny"));
                    DummyValuesPasser.setLongValue(temp.get(0).getId());
                    DummyValuesPasser.setStringValue(temp.get(0).getModel());
                    Stage stage = (Stage) table.getScene().getWindow();
                    stage.close();
                }
            });
            return row ;
        });
    }

    @Override
    protected void initializeTable(){
        dao = new ModelRoweruDao();
        List<TableColumn<ModelRoweru, ?>> columns = new ArrayList<>(table.getColumns());
        String[] attribNames = {"nazwa", "typ", "rozmiar", "cenaZaDzien", "cenaZaMiesiac", "opis", "wolne"};
        TableUtils.initializeAttribColumns(columns, attribNames);
        activateTableRowsOnClickFunc();
        String[] params = {"nazwa in (select model_roweru_nazwa from ROWERY where status = 'wolny')"};
        table.setItems(FXCollections.observableList(dao.get(params)));
        table.setEditable(true);
        table.setPlaceholder(new Label(EmptyText));
    }

}
