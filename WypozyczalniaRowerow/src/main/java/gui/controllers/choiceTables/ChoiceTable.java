package gui.controllers.choiceTables;

import gui.controllers.BasicTable;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;
import utils.DummyValuesPasser;

/**
 * parent class for all fxml controllers classes which control choosing an element
 * @param <T> class type of elements stored in the TableView displayed in the window to which controller is assigned
 */
public abstract class ChoiceTable<T> extends BasicTable<T> {

    protected void activateTableRowsOnClickFunc(){
        table.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    T rowData = row.getItem();
                    DummyValuesPasser.setStringValue(dao.getKeyValue(rowData));
                    Stage stage = (Stage) table.getScene().getWindow();
                    stage.close();
                }
            });
            return row;
        });
    }

    @Override
    protected void initializeTable(){
        activateTableRowsOnClickFunc();
        super.initializeTable();
    }

}
