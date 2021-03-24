package database.daos;

import database.objects.Klient;
import utils.AlertsUtil;

import java.sql.ResultSet;
import java.util.List;

public class KlientDao extends Dao<Klient>{
    private static boolean SearchParamsReady = false;
    private static Klient SearchObj = null;

    private static final String[] ColNames = {"nr_dowodu", "imie", "nazwisko", "nr_telefonu"};
    private static final String TableName = "klienci";

    public KlientDao(){
        super(ColNames, TableName);
    }

    @Override
    protected Klient createObject(ResultSet rs){
        try{
            return new Klient(rs.getString(ColNames[0]), rs.getString(ColNames[1]), rs.getString(ColNames[2]), rs.getInt(ColNames[3]));
        }catch(Exception e){
            AlertsUtil.showErrorAlert(null, "Błąd tworzenia obiektu",
                    "Wystąpił błąd podczas zapisu informacji pobranych z bazy danych.");
        }
        return null;
    }

    @Override
    public String getKey(Klient obj){
        return "nr_dowodu='" + obj.getNr_dowodu() + "'";
    }

    @Override
    public String getKeyValue(Klient obj) {
        return obj.getNr_dowodu();
    }

    @Override
    public String getAttribForName(String name, Klient obj){
        if(name.equalsIgnoreCase(colNames[0])){
            return "'" + obj.getNr_dowodu() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[1])){
            return "'" + obj.getImie() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[2])){
            return "'" + obj.getNazwisko() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[3])){
            if(obj.getNr_telefonu() != 0){
                return "" + obj.getNr_telefonu();
            }
        }
        return "null";
    }

    @Override
    protected String getInsertionValuesTemplate(Klient klient){
        return "(?, ?, ?, ?)";
    }

    @Override
    protected String getIdentyficationTemplate() {
        return "nr_dowodu=?";
    }

    @Override
    protected String getSearchParamsTemplate(Klient klient) {
        StringBuilder template = new StringBuilder();
        boolean first = true;
        if(klient.getNr_dowodu() != null){
            template.append(" where ").append(colNames[0]).append("=?");
            first = false;
        }
        if(klient.getImie() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[1]).append("=?");
        }
        if(klient.getNazwisko() != null){
            if(!first) template.append((" and "));
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[2]).append("=?");
        }
        if(klient.getNr_telefonu() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[3]).append("=?");
        }
        return template.toString();
    }

    @Override
    public List<Klient> getWithPresetParams() {
        SearchParamsReady = false;
        return get(SearchObj);
    }

    @Override
    public void setSearchParams(Klient klient) {
        SearchObj = klient;
        SearchParamsReady = true;
    }

    @Override
    public boolean ifSerchParamsReady(){
        return SearchParamsReady;
    }
}
