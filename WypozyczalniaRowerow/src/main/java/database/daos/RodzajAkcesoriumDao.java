package database.daos;

import database.DatabaseConnection;
import database.objects.RodzajAkcesorium;
import utils.AlertsUtil;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class RodzajAkcesoriumDao extends Dao<RodzajAkcesorium> {
    private static boolean SearchParamsReady = false;
    private static RodzajAkcesorium SearchObj = null;

    private static final String[] ColNames = {"nazwa", "cena_za_dzien", "cena_za_miesiac", "kaucja"};
    private static final String TableName = "rodzaje_akcesorium";

    public RodzajAkcesoriumDao(){
        super(ColNames, TableName);
    }

    @Override
    protected RodzajAkcesorium createObject(ResultSet rs) {
        try{
            RodzajAkcesorium rodzajAkcesorium = new RodzajAkcesorium(rs.getString(ColNames[0]), rs.getFloat(ColNames[1]),
                    rs.getFloat(ColNames[2]), rs.getFloat(ColNames[3]));
            int wolne = 0;
            try (CallableStatement stmt = DatabaseConnection.getInstance()
                    .getConnection().prepareCall("{? = call PoliczWolneAkcesoria(?)}")) {
                stmt.setString(2, rodzajAkcesorium.getNazwa());
                stmt.registerOutParameter(1, Types.INTEGER);
                stmt.execute();
                wolne = stmt.getInt(1);
            } catch(SQLException e){
                AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                        "Wystąpił błąd podczas pobierania informacji ilości dostępnych akcesoriów.");
            }
            rodzajAkcesorium.setWolne(wolne);
            return rodzajAkcesorium;
        }catch(Exception e){
            AlertsUtil.showErrorAlert(null, "Błąd tworzenia obiektu",
                    "Wystąpił błąd podczas zapisu informacji pobranych z bazy danych.");
        }
        return null;
    }

    @Override
    public String getAttribForName(String name, RodzajAkcesorium obj) {
        if(name.equalsIgnoreCase(colNames[0])){
            return "'" + obj.getNazwa() + "'";
        }
        else if(name.equalsIgnoreCase(colNames[1])){
            return String.format("%.2f", obj.getCenaZaDzien());
        }
        else if(name.equalsIgnoreCase(colNames[2])){
            return String.format("%.2f", obj.getCenaZaMiesiac());
        }
        else if(name.equalsIgnoreCase(colNames[3])){
            return String.format("%.2f", obj.getKaucja());
        }
        return "null";
    }

    @Override
    public String getKey(RodzajAkcesorium obj) {
        return "nazwa='" + obj.getNazwa() + "'";
    }

    @Override
    public String getKeyValue(RodzajAkcesorium obj) {
        return obj.getNazwa();
    }

    @Override
    protected String getInsertionValuesTemplate(RodzajAkcesorium rodzajAkcesorium) {
        StringBuilder template = new StringBuilder("(?, ");
        template.append(rodzajAkcesorium.getCenaZaDzien()>0?"?":"default").append(", ");
        template.append(rodzajAkcesorium.getCenaZaMiesiac()>0?"?":"default").append(", ");
        template.append(rodzajAkcesorium.getKaucja()>0?"?":"default").append(")");
        return template.toString();
    }

    @Override
    protected String getIdentyficationTemplate() {
        return "nazwa=?";
    }

    @Override
    protected String getSearchParamsTemplate(RodzajAkcesorium rodzajAkcesorium) {
        StringBuilder template = new StringBuilder();
        boolean first = true;
        if(rodzajAkcesorium.getNazwa() != null){
            template.append(" where ").append(colNames[0]).append("=?");
            first = false;
        }
        if(rodzajAkcesorium.getCenaZaDzien() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[1]).append("=?");
        }
        if(rodzajAkcesorium.getCenaZaMiesiac() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[2]).append("=?");
        }
        if(rodzajAkcesorium.getKaucja() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[3]).append("=?");
        }
        return template.toString();
    }

    @Override
    public List<RodzajAkcesorium> getWithPresetParams() {
        SearchParamsReady = false;
        return get(SearchObj);
    }

    @Override
    public void setSearchParams(RodzajAkcesorium rodzajAkcesorium) {
        SearchObj = rodzajAkcesorium;
        SearchParamsReady = true;
    }

    @Override
    public boolean ifSerchParamsReady(){
        return SearchParamsReady;
    }

}
