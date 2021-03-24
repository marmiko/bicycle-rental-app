package database.daos;

import database.objects.Przeglad;
import utils.AlertsUtil;

import java.sql.ResultSet;
import java.util.List;

public class PrzegladDao extends Dao<Przeglad> {
    private static boolean SearchParamsReady = false;
    private static Przeglad SearchObj = null;

    private static final String[] ColNames = {"data_wykonania", "opis", "rower", "pracownik"};
    private static final String TableName = "przeglady_techniczne";

    public PrzegladDao(){
        super(ColNames, TableName);
    }

    @Override
    protected Przeglad createObject(ResultSet rs) {
        try{
            return new Przeglad(rs.getDate(ColNames[0]), rs.getString(ColNames[1]),
                    rs.getLong(ColNames[2]), rs.getLong(ColNames[3]));
        }catch(Exception e){
            AlertsUtil.showErrorAlert(null, "Błąd tworzenia obiektu",
                    "Wystąpił błąd podczas zapisu informacji pobranych z bazy danych.");
        }
        return null;
    }

    @Override
    public String getAttribForName(String name, Przeglad obj) {
        if(name.equalsIgnoreCase(colNames[0])){
            if(obj.getDataWykonania() != null) {
                return "TO_DATE('" + obj.getDataWykonania().toString() + "', 'yyyy-mm-dd')";
            }
            else{
                return "default";
            }
        }
        else if(name.equalsIgnoreCase(colNames[1])){
            if(obj.getOpis() != null) {
                return "'" + obj.getOpis() + "'";
            }
        }
        else if(name.equalsIgnoreCase(colNames[2])){
            return "" + obj.getIdRoweru();
        }
        else if(name.equalsIgnoreCase(colNames[3])){
            return "" + obj.getPracownik();
        }
        return "null";
    }

    @Override
    public String getKey(Przeglad obj) {
        return "data_wykonania=" + "TO_DATE('" + obj.getDataWykonania().toString() + "', 'yyyy-mm-dd')" +
                " and rower=" + obj.getIdRoweru();
    }

    @Override
    public String getKeyValue(Przeglad obj) {
        return "data_wykonania=" + "TO_DATE('" + obj.getDataWykonania().toString() + "', 'yyyy-mm-dd')";
    }

    @Override
    protected String getInsertionValuesTemplate(Przeglad przeglad) {
        StringBuilder template = new StringBuilder("(");
        template.append(przeglad.getDataWykonania()!=null?"?":"default").append(", ");
        template.append(przeglad.getOpis()!=null?"?":"null").append(", ?, ?)");
        return template.toString();
    }

    @Override
    protected String getIdentyficationTemplate() {
        return "data_wykonania=? and rower=?";
    }

    @Override
    protected String getSearchParamsTemplate(Przeglad przeglad) {
        StringBuilder template = new StringBuilder();
        boolean first = true;
        if(przeglad.getDataWykonania() != null){
            template.append(" where ").append(colNames[0]).append("=?");
            first = false;
        }
        if(przeglad.getOpis() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[1]).append("=?");
        }
        if(przeglad.getIdRoweru() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[2]).append("=?");
        }
        if(przeglad.getPracownik() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[3]).append("=?");
        }
        return template.toString();
    }

    @Override
    public List<Przeglad> getWithPresetParams() {
        SearchParamsReady = false;
        return get(SearchObj);
    }

    @Override
    public void setSearchParams(Przeglad przeglad) {
        SearchObj = przeglad;
        SearchParamsReady = true;
    }

    @Override
    public boolean ifSerchParamsReady(){
        return SearchParamsReady;
    }
}
