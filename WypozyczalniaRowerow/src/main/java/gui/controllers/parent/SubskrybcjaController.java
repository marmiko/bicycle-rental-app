package gui.controllers.parent;

import database.daos.SubskrybcjaDao;
import database.objects.Subskrybcja;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.sql.Date;

/**
 * parent controller class for Subskrybcja objects insertion, modification and serach fxml files,
 * provides basic foundation and common functionality for specialised classes;
 * extends WypoczyczenieController, due to common features of Wypozyczenie and Subskrybcja objects
 */
public abstract class SubskrybcjaController extends WypozyczenieController implements Initializable {

    protected static boolean rowerChoosable = true;
    protected static String RowerID;
    protected static String RowerModel;

    protected SubskrybcjaDao subskrybcjaDao = new SubskrybcjaDao();

    protected void initializeButtonDodaj(){
        dodaj.setOnAction(new EventHandler<>() {

            @Override
            public void handle(ActionEvent e) {
                if(klient.getLength() != 0 &&
                        rowerID.getLength() != 0 &&
                        pracownik.getLength() != 0){
                    infoAlert.setText("");
                    Date convertedDataRozpoczecia = null;
                    if(dataWypozyczenia.getValue() != null){
                        convertedDataRozpoczecia = Date.valueOf(dataWypozyczenia.getValue());
                    }
                    long convertedIdAkcesorium = -1;
                    long convertedIdPrac = -1;
                    long convertedIdroweru = -1;
                    try{
                        if(akcesoriumID.getLength()>0){
                            convertedIdAkcesorium = Long.parseLong(akcesoriumID.getText());
                        }
                        convertedIdPrac = Long.parseLong(pracownik.getText().trim());
                        convertedIdroweru = Long.parseLong(rowerID.getText().trim());
                    }  catch (Exception ex) {
                        infoAlert.setText("Błędny format liczby");
                        return;
                    }
                    subskrybcjaDao.insert(new Subskrybcja(convertedDataRozpoczecia,
                            klient.getText().trim(), convertedIdPrac,
                            convertedIdroweru,
                            convertedIdAkcesorium, null));
                    Stage stage = (Stage) dodaj.getScene().getWindow();
                    stage.close();
                }
                else{
                    infoAlert.setText("Wypełnij wszystkie pola oznaczone gwiazdką.");
                }
            }

        });
    }

    public static void enterRowerMode(String id, String model){
        rowerChoosable = false;
        RowerID = id;
        RowerModel = model;
    }

    public static void exitRowerMode(){
        rowerChoosable = true;
        RowerID = null;
        RowerModel = null;
    }
}
