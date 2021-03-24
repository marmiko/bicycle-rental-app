package database.daos;

import database.DatabaseConnection;
import database.objects.Wypozyczenie;
import utils.AlertsUtil;

import java.sql.*;
import java.util.List;

public class WypozyczenieDao extends Dao<Wypozyczenie>{
    private static boolean ActiveOnly = false;
    private static boolean SearchParamsReady = false;
    private static Wypozyczenie SearchObj = null;

    private static final String[] ColNames = {"id_wypozyczenia", "data_wypozyczenia", "data_zwrotu", "klient_nr_dowodu",
            "id_prac", "id_roweru", "id_akcesorium"};
    private static final String TableName = "wypozyczenia";

    public WypozyczenieDao(){
        super(ColNames, TableName);
    }

    @Override
    protected Wypozyczenie createObject(ResultSet rs){
        try{
            Wypozyczenie wypozyczenie = new Wypozyczenie(rs.getLong(ColNames[0]), rs.getDate(ColNames[1]),
                    rs.getDate(ColNames[2]), rs.getString(ColNames[3]),
                    rs.getLong(ColNames[4]), rs.getLong(ColNames[5]), rs.getLong(ColNames[6]));
            double cena = 0;
            try (CallableStatement stmt = DatabaseConnection.getInstance()
                    .getConnection().prepareCall("{? = call CenaWypozyczenia(?)}")) {
                stmt.setLong(2, wypozyczenie.getId_wypozyczenia());
                stmt.registerOutParameter(1, Types.NUMERIC);
                stmt.execute();
                cena = stmt.getDouble(1);
            } catch(SQLException e){
                AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                        "Wystąpił błąd podczas pobierania informacji o aktualnej cenie wypożyczenia.");
            }
            wypozyczenie.setAktualnaCena(cena);
            return wypozyczenie;
        }catch(Exception e){
            AlertsUtil.showErrorAlert(null, "Błąd tworzenia obiektu",
                    "Wystąpił błąd podczas zapisu informacji pobranych z bazy danych.");
        }
        return null;
    }

    @Override
    public String getKey(Wypozyczenie obj){
        return "id_wypozyczenia=" + obj.getId_wypozyczenia();
    }

    @Override
    public String getKeyValue(Wypozyczenie obj) {
        return "" + obj.getId_wypozyczenia();
    }

    @Override
    public String getAttribForName(String name, Wypozyczenie obj){
        if(name.equalsIgnoreCase(colNames[0])){
            return "" + obj.getId_wypozyczenia();
        }
        else if(name.equalsIgnoreCase(colNames[1])){
            return "TO_DATE('" + obj.getData_wypozyczenia().toString() + "', 'yyyy-mm-dd')";
        }
        else if(name.equalsIgnoreCase(colNames[2])){
            if(obj.getData_zwrotu() != null){
                return "TO_DATE('" + obj.getData_zwrotu().toString() + "', 'yyyy-mm-dd')";
            }
        }
        else if(name.equalsIgnoreCase(colNames[3])){
            return "'" + obj.getKlient_nr_dowodu() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[4])){
            return "" + obj.getId_prac();
        }
        else if(name.equalsIgnoreCase(colNames[5])){
            return "" + obj.getId_roweru();
        }
        else if(name.equalsIgnoreCase(colNames[6])){
            if(obj.getId_akcesoria() != 0){
                return "" + obj.getId_akcesoria();
            }
        }
        return "null";
    }

    @Override
    protected String getInsertionValuesTemplate(Wypozyczenie wypozyczenie) {
        StringBuilder template = new StringBuilder("(");
        template.append(wypozyczenie.getData_wypozyczenia()!=null?"?":"default").append(", ");
        template.append("null, ?, ?, ?, ").append(wypozyczenie.getId_akcesoria()>0?"?":"null").append(", ");
        template.append("default)");
        return template.toString();
    }

    @Override
    protected String getIdentyficationTemplate() {
        return "id_wypozyczenia=?";
    }

    @Override
    protected String getSearchParamsTemplate(Wypozyczenie wypozyczenie) {
        StringBuilder template = new StringBuilder();
        boolean first = true;
        if(wypozyczenie.getId_wypozyczenia() > 0){
            template.append(" where ").append(colNames[0]).append("=?");
            first = false;
        }
        if(wypozyczenie.getData_wypozyczenia() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[1]).append("=?");
        }
        if(wypozyczenie.getData_zwrotu() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[2]).append("=?");
        }
        if(wypozyczenie.getKlient_nr_dowodu() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[3]).append("=?");
        }
        if(wypozyczenie.getId_prac() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[4]).append("=?");
        }
        if(wypozyczenie.getId_roweru() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[5]).append("=?");
        }
        if(wypozyczenie.getId_akcesoria() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[6]).append("=?");
        }
        return template.toString();
    }

    @Override
    public List<Wypozyczenie> getWithPresetParams() {
        SearchParamsReady = false;
        return get(SearchObj);
    }

    @Override
    public void setSearchParams(Wypozyczenie wypozyczenie) {
        SearchObj = wypozyczenie;
        SearchParamsReady = true;
    }

    @Override
    public boolean ifSerchParamsReady(){
        return SearchParamsReady;
    }

    @Override
    public List<Wypozyczenie> getAll(){
        if(!ActiveOnly){
            return super.getAll();
        }
        else{
            String[] params = {"DATA_ZWROTU IS NULL"};
            return super.get(params);
        }
    }

    public static void setActiveOnly(boolean value){
        ActiveOnly = value;
    }

}
