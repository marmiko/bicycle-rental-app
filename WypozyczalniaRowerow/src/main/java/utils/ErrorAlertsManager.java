package utils;

import java.util.Arrays;

public class ErrorAlertsManager {

    public static void displayError(String errorMessage){
        if(errorMessage.contains("CHK_WYPOZYCZENIA_DATY")){
            AlertsUtil.showErrorAlert(null, "Bład operacji",
                    "Data zwrotu musi być poźniejsza od daty wypożyczenia.");
        }
        else if(errorMessage.contains("SUBSKRYBCJE_CHK1")){
            AlertsUtil.showErrorAlert(null, "Bład operacji",
                    "Data zakończenia musi być poźniejsza od daty rozpoczęcia.");
        }
        else if(errorMessage.contains("CHK_USTERKI_DATY")){
            AlertsUtil.showErrorAlert(null, "Bład operacji",
                    "Data naprawy nie może być wcześniejsza od daty zgłoszenia.");
        }
        else if(errorMessage.contains("WYPOZYCZENIA_KLIENT_FK") ||
                errorMessage.contains("SUBSKRYBCJE_KLIENT_FK")){
            AlertsUtil.showErrorAlert(null, "Błąd operacji",
                    "Podany klient nie istnieje.");
        }
        else if(errorMessage.contains("WYPOZYCZENIA_PRACOWNIK_FK") ||
                errorMessage.contains("SUBSKRYBCJE_PRACOWNIK_FK") ||
                errorMessage.contains("PRZEGLADY_TECHNICZNE_PRACOWNIK_FK")){
            AlertsUtil.showErrorAlert(null, "Błąd operacji",
                    "Podany pracownik nie istnieje.");
        }
        else if(errorMessage.contains("CHK_ROWERY_STATUS")){
            AlertsUtil.showErrorAlert(null, "Bład operacji",
                    "Status roweru musi być jednym z podanych: \n"
                            + Arrays.toString(DatabaseRestrictedValuesStorage.RowerStatusValues));
        }
        else if(errorMessage.contains("CHK_STATUS_AKCESORIUM")){
            AlertsUtil.showErrorAlert(null, "Bład operacji",
                    "Status akcesorium musi być jednym z podanych: \n"
                            + Arrays.toString(DatabaseRestrictedValuesStorage.AkcesoriumStatusValues));
        }
        else if(errorMessage.contains("ROWER_MODEL_ROWERU_FK")){
            AlertsUtil.showErrorAlert(null, "Bład operacji",
                    "Podany model roweru nie istnieje.");
        }
        else if(errorMessage.contains("CHK_RODZAJE_AKCESORIUM_KAUCJA") ||
                errorMessage.contains("CHK_RODZAJE_AKCESORIUM_CENA_ZA_DZIEN") ||
                errorMessage.contains("CHK_RODZAJE_AKCESORIUM_CENA_ZA_MIESIAC") ||
                errorMessage.contains("CHK_MODELE_ROWEROW_CENA_ZA_DZIEN") ||
                errorMessage.contains("CHK_MODELE_ROWEROW_CENA_ZA_MIESIAC")){
            AlertsUtil.showErrorAlert(null, "Bład operacji",
                    "Kwota musi być większa od 0.");
        }
        else if(errorMessage.contains("CHK_KLIENCI")){
            AlertsUtil.showErrorAlert(null, "Bład operacji",
                    "Niepoprawny numer telefonu.");
        }
        else if(errorMessage.contains("value too large for column")){
            AlertsUtil.showErrorAlert(null, "Bład operacji",
                    "Wprowadzona wartość jest zbyt długa.");
        }
        else{
            AlertsUtil.showErrorAlert(null, "Błąd operacji",
                    errorMessage.split("\\r?\\n")[0].split("ORA-[0-9]+:")[1]);
        }
    }

}
