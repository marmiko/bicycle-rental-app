package utils;

import exceptions.InvalidCenaException;
import javafx.scene.control.TextField;

public class PriceChecker {

    public static Double getCena(TextField t) throws InvalidCenaException {
        String tString = t.getText().trim().replace(",",".");
        String []tStringArr = tString.split("\\.");

        if(tStringArr.length > 2){
            throw new InvalidCenaException("Niepoprawna cena");
        }

        if(tStringArr.length == 2 && tStringArr[1].length() >2){
            throw new InvalidCenaException("Niepoprawna cena");
        }


        try {
            return Double.parseDouble(tString);
        }catch(Exception e){
            e.printStackTrace();
            throw new InvalidCenaException("Niepoprawna cena");
        }

    }

}
