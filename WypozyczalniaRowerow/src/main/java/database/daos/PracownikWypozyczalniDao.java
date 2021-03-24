package database.daos;

import database.objects.PracownikWypozyczalni;
import utils.AlertsUtil;

import java.sql.ResultSet;
import java.util.List;

public class PracownikWypozyczalniDao extends Dao<PracownikWypozyczalni>{
    private static boolean SearchParamsReady = false;
    private static PracownikWypozyczalni SearchObj = null;

    private static final String[] ColNames = {"imie", "nazwisko", "id_prac", "data_zatrudnienia", "data_zwolnienia"};
    private static final String TableName = "pracownicy_wypozyczalni";

    public PracownikWypozyczalniDao(){
        super(ColNames, TableName);
    }

    @Override
    protected PracownikWypozyczalni createObject(ResultSet rs) {
        try{
            return new PracownikWypozyczalni(rs.getString(ColNames[0]), rs.getString(ColNames[1]),
                    rs.getLong(ColNames[2]), rs.getDate(ColNames[3]), rs.getDate(ColNames[4]));
        }catch(Exception e){
            AlertsUtil.showErrorAlert(null, "Błąd tworzenia obiektu",
                    "Wystąpił błąd podczas zapisu informacji pobranych z bazy danych.");
        }
        return null;
    }

    @Override
    public String getKey(PracownikWypozyczalni obj){
        return "id_prac=" + obj.getId();
    }

    @Override
    public String getKeyValue(PracownikWypozyczalni obj) {
        return "" + obj.getId();
    }

    @Override
    public String getAttribForName(String name, PracownikWypozyczalni obj){
        if(name.equalsIgnoreCase(colNames[0])){
            return "'" + obj.getImie() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[1])){
            return "'" + obj.getNazwisko() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[2])){
            return "" + obj.getId();
        }
        else if(name.equalsIgnoreCase(colNames[3])){
            return "TO_DATE(" + obj.getDataZatrudnienia().toString() + ")";
        }
        else if(name.equalsIgnoreCase(colNames[4])){
            if(obj.getDataZwolnienia() != null){
                return "TO_DATE(" + obj.getDataZwolnienia().toString() + ")";
            }
        }
        return "null";
    }

    @Override
    protected String getInsertionValuesTemplate(PracownikWypozyczalni pracownikWypozyczalni) {
        StringBuilder template = new StringBuilder("(?, ?, default, ");
        template.append(pracownikWypozyczalni.getDataZatrudnienia()!=null?"?":"default").append(", null)");
        return template.toString();
    }

    @Override
    protected String getIdentyficationTemplate() {
        return "id_prac=?";
    }

    @Override
    protected String getSearchParamsTemplate(PracownikWypozyczalni pracownikWypozyczalni) {
        StringBuilder template = new StringBuilder();
        boolean first = true;
        if(pracownikWypozyczalni.getImie() != null){
            template.append(" where ").append(colNames[0]).append("=?");
            first = false;
        }
        if(pracownikWypozyczalni.getNazwisko() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[1]).append("=?");
        }
        if(pracownikWypozyczalni.getId() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[2]).append("=?");
        }
        if(pracownikWypozyczalni.getDataZatrudnienia() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[3]).append("=?");
        }
        if(pracownikWypozyczalni.getDataZwolnienia() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[4]).append("=?");
        }
        return template.toString();
    }

    @Override
    public List<PracownikWypozyczalni> getWithPresetParams() {
        SearchParamsReady = false;
        return get(SearchObj);
    }

    @Override
    public void setSearchParams(PracownikWypozyczalni pracownikWypozyczalni) {
        SearchObj = pracownikWypozyczalni;
        SearchParamsReady = true;
    }

    @Override
    public boolean ifSerchParamsReady(){
        return SearchParamsReady;
    }
}
