package database.daos;

import database.objects.Rower;
import utils.AlertsUtil;

import java.sql.ResultSet;
import java.util.List;

public class RowerDao extends Dao<Rower> {
    private static boolean SearchParamsReady = false;
    private static Rower SearchObj = null;

    private static final String[] ColNames = {"id_roweru", "kolor", "data_zakupu", "model_roweru_nazwa", "status"};
    private static final String TableName = "rowery";

    public RowerDao() {
        super(ColNames, TableName);
    }

    @Override
    protected Rower createObject(ResultSet rs) {
        try {
            return new Rower(rs.getLong(ColNames[0]), rs.getString(ColNames[1]),
                    rs.getDate(ColNames[2]), rs.getString(ColNames[3]), rs.getString(ColNames[4]));
        } catch (Exception e) {
            AlertsUtil.showErrorAlert(null, "Błąd tworzenia obiektu",
                    "Wystąpił błąd podczas zapisu informacji pobranych z bazy danych.");
        }
        return null;
    }

    @Override
    public String getKey(Rower obj) {
        return "id_roweru=" + obj.getId();
    }

    @Override
    public String getKeyValue(Rower obj) {
        return "" + obj.getId();
    }

    @Override
    public String getAttribForName(String name, Rower obj) {
        if (name.equalsIgnoreCase(colNames[0])) {
            return "" + obj.getId();
        } else if (name.equalsIgnoreCase(colNames[1])) {
            return "'" + obj.getKolor() + "'";
        } else if (name.equalsIgnoreCase(colNames[2])) {
            return "TO_DATE('" + obj.getDataZakupu().toString() + "', 'yyyy-mm-dd')";
        } else if (name.equalsIgnoreCase(colNames[3])) {
            return "'" + obj.getModel() + "'";
        } else if (name.equalsIgnoreCase(colNames[4])) {
            return "'" + obj.getStatus() + "'";
        }
        return "null";
    }

    @Override
    protected String getInsertionValuesTemplate(Rower rower) {
        StringBuilder template = new StringBuilder("(default, ?, ");
        template.append(rower.getDataZakupu() != null ? "?" : "default").append(", ?, default)");
        return template.toString();
    }

    @Override
    protected String getIdentyficationTemplate() {
        return "id_roweru=?";
    }

    @Override
    protected String getSearchParamsTemplate(Rower rower) {
        StringBuilder template = new StringBuilder();
        boolean first = true;
        if(rower.getId() > 0){
            template.append(" where ").append(colNames[0]).append("=?");
            first = false;
        }
        if(rower.getKolor() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[1]).append("=?");
        }
        if(rower.getDataZakupu() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[2]).append("=?");
        }
        if(rower.getModel() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[3]).append("=?");
        }
        if(rower.getStatus() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[4]).append("=?");
        }
        return template.toString();
    }

    @Override
    public List<Rower> getWithPresetParams() {
        SearchParamsReady = false;
        return get(SearchObj);
    }

    @Override
    public void setSearchParams(Rower rower) {
        SearchObj = rower;
        SearchParamsReady = true;
    }

    @Override
    public boolean ifSerchParamsReady(){
        return SearchParamsReady;
    }
}

