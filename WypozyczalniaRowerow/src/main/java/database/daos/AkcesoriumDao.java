package database.daos;

import database.objects.Akcesorium;
import utils.AlertsUtil;

import java.sql.ResultSet;
import java.util.List;

public class AkcesoriumDao extends Dao<Akcesorium>{
    private static boolean SearchParamsReady = false;
    private static Akcesorium SearchObj=null;

    private static final String[] ColNames = {"id_akcesorium", "data_zakupu", "rodzaj_akcesorium_nazwa", "status"};
    private static final String TableName = "akcesoria";

    public AkcesoriumDao(){
        super(ColNames, TableName);
    }

    @Override
    protected Akcesorium createObject(ResultSet rs) {
        try{
            return new Akcesorium(rs.getLong(ColNames[0]), rs.getDate(ColNames[1]), rs.getString(ColNames[2]),
                    rs.getString(ColNames[3]));
        }catch(Exception e){
            AlertsUtil.showErrorAlert(null, "Błąd tworzenia obiektu",
                    "Wystąpił błąd podczas zapisu informacji pobranych z bazy danych.");
        }
        return null;
    }

    @Override
    public String getKey(Akcesorium obj){
        return "id_akcesorium=" + obj.getId();
    }

    @Override
    public String getKeyValue(Akcesorium obj) {
        return "" + obj.getId();
    }

    @Override
    public String getAttribForName(String name, Akcesorium obj){
        if(name.equalsIgnoreCase(colNames[0])){
            return "" + obj.getId();
        }
        else if(name.equalsIgnoreCase(colNames[1])){
            return "TO_DATE('" + obj.getDataZakupu().toString() + "', 'yyyy-mm-dd')";
        }
        else if(name.equalsIgnoreCase(colNames[2])){
            return "'" + obj.getRodzaj() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[3])){
            return "'" + obj.getStatus() + "'";
        }
        return "null";
    }

    @Override
    protected String getInsertionValuesTemplate(Akcesorium akcesorium){
        StringBuilder template = new StringBuilder("(");
        template.append("default, ").append(akcesorium.getDataZakupu()!=null?"?":"default").append(", ");
        template.append("?, default)");
        return template.toString();
    }

    @Override
    protected String getIdentyficationTemplate() {
        return "id_akcesorium=?";
    }

    @Override
    protected String getSearchParamsTemplate(Akcesorium akcesorium) {
        StringBuilder template = new StringBuilder();
        boolean first = true;
        if(akcesorium.getId()>0){
            template.append(" where ");
            template.append(colNames[0]).append("=?");
            first = false;
        }
        if(akcesorium.getDataZakupu() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[1]).append("=?");
        }
        if(akcesorium.getRodzaj() != null) {
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[2]).append("=?");
        }
        if(akcesorium.getStatus() != null){
            if(!first) template.append((" and "));
            else template.append(" where ");
            template.append(colNames[3]).append("=?");
        }
        return template.toString();
    }

    @Override
    public List<Akcesorium> getWithPresetParams() {
        SearchParamsReady = false;
        return get(SearchObj);
    }

    @Override
    public void setSearchParams(Akcesorium akcesorium) {
        SearchObj = akcesorium;
        SearchParamsReady = true;
    }

    @Override
    public boolean ifSerchParamsReady(){
        return SearchParamsReady;
    }
}
