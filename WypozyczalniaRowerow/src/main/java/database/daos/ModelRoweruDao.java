package database.daos;

import database.DatabaseConnection;
import database.objects.ModelRoweru;
import utils.AlertsUtil;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class ModelRoweruDao extends Dao<ModelRoweru> {
    private static boolean SearchParamsReady = false;
    private static ModelRoweru SearchObj = null;

    private static final String[] ColNames = {"nazwa", "typ", "rozmiar", "cena_za_dzien", "opis", "cena_za_miesiac"};
    private static final String TableName = "modele_rowerow";

    public ModelRoweruDao(){
        super(ColNames, TableName);
    }

    @Override
    protected ModelRoweru createObject(ResultSet rs) {
        try{
            ModelRoweru modelRoweru = new ModelRoweru(rs.getString(ColNames[0]), rs.getString(ColNames[1]),
                    rs.getString(ColNames[2]), rs.getFloat(ColNames[3]), rs.getString(ColNames[4]),
                    rs.getFloat(ColNames[5]));
            int wolne = 0;
            try (CallableStatement stmt = DatabaseConnection.getInstance()
                    .getConnection().prepareCall("{? = call PoliczWolneRowery(?)}")) {
                stmt.setString(2, modelRoweru.getNazwa());
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.execute();
                wolne = stmt.getInt(1);
            } catch(SQLException e){
                AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                        "Wystąpił błąd podczas pobierania informacji ilości dostępnych rowerów.");
            }
            modelRoweru.setWolne(wolne);
            return modelRoweru;
        }catch(Exception e){
            AlertsUtil.showErrorAlert(null, "Błąd tworzenia obiektu",
                    "Wystąpił błąd podczas zapisu informacji pobranych z bazy danych.");
        }
        return null;
    }

    @Override
    public String getKey(ModelRoweru obj){
        return "nazwa='" + obj.getNazwa() + "'";
    }

    @Override
    public String getKeyValue(ModelRoweru obj) {
        return obj.getNazwa();
    }

    @Override
    public String getAttribForName(String name, ModelRoweru obj){
        if(name.equalsIgnoreCase(colNames[0])){
            return "'" + obj.getNazwa() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[1])){
            return "'" + obj.getTyp() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[2])){
            return "'" + obj.getRozmiar() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[3])){
            if(obj.getCenaZaDzien() == -1) return "default";
            return String.format("%.2f", obj.getCenaZaDzien());
        }
        else if(name.equalsIgnoreCase(colNames[4])){
            if(obj.getOpis() != null){
                return "'" + obj.getOpis() + "'";
            }
        }
        else if(name.equalsIgnoreCase(colNames[5])){
            if(obj.getCenaZaMiesiac() == -1) return "default";
            return String.format("%.2f", obj.getCenaZaMiesiac());
        }
        return "null";
    }

    @Override
    protected String getInsertionValuesTemplate(ModelRoweru modelRoweru) {
        StringBuilder template = new StringBuilder("(?, ?, ?, ");
        template.append(modelRoweru.getCenaZaDzien()>0?"?":"default").append(", ");
        template.append(modelRoweru.getOpis()==null?"null":"?").append(", ");
        template.append(modelRoweru.getCenaZaMiesiac()>0?"?":"default").append(")");
        return template.toString();
    }

    @Override
    protected String getIdentyficationTemplate() {
        return "nazwa=?";
    }

    @Override
    protected String getSearchParamsTemplate(ModelRoweru modelRoweru) {
        StringBuilder template = new StringBuilder();
        boolean first = true;
        if(modelRoweru.getNazwa() != null){
            template.append(" where ").append(colNames[0]).append("=?");
            first = false;
        }
        if(modelRoweru.getTyp() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[1]).append("=?");
        }
        if(modelRoweru.getRozmiar() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[2]).append("=?");
        }
        if(modelRoweru.getCenaZaDzien() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[3]).append("=?");
        }
        if(modelRoweru.getOpis() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[4]).append("=?");
        }
        if(modelRoweru.getCenaZaMiesiac() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[5]).append("=?");
        }
        return template.toString();
    }

    @Override
    public List<ModelRoweru> getWithPresetParams() {
        SearchParamsReady = false;
        return get(SearchObj);
    }

    @Override
    public void setSearchParams(ModelRoweru modelRoweru) {
        SearchObj = modelRoweru;
        SearchParamsReady = true;
    }

    @Override
    public boolean ifSerchParamsReady(){
        return SearchParamsReady;
    }

}
