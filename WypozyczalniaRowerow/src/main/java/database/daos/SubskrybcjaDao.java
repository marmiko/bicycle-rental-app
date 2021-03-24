package database.daos;

import database.DatabaseConnection;
import database.objects.Subskrybcja;
import utils.AlertsUtil;

import java.sql.*;
import java.util.List;

public class SubskrybcjaDao extends Dao<Subskrybcja>{
    private static boolean ActiveOnly = false;
    private static boolean SearchParamsReady = false;
    private static Subskrybcja SearchObj = null;

    private static final String[] ColNames = {"data_rozpoczecia", "klient_nr_dowodu", "id_prac", "id_roweru",
            "id_akcesorium", "data_zakonczenia"};
    private static final String TableName = "subskrybcje";

    public SubskrybcjaDao(){
        super(ColNames, TableName);
    }

    @Override
    protected Subskrybcja createObject(ResultSet rs){
        try{
            Subskrybcja subskrybcja = new Subskrybcja(rs.getDate(colNames[0]), rs.getString(colNames[1]),
                    rs.getLong(colNames[2]), rs.getLong(colNames[3]), rs.getLong(colNames[4]), rs.getDate(colNames[5]));
            double cena = 0;
            try (CallableStatement stmt = DatabaseConnection.getInstance()
                    .getConnection().prepareCall("{? = call CenaZaMiesiacSubskrybcja(?, ?)}")) {
                stmt.setLong(2, subskrybcja.getIdRoweru());
                stmt.setLong(3, subskrybcja.getIdAkcesorium());
                stmt.registerOutParameter(1, Types.NUMERIC);
                stmt.execute();
                cena = stmt.getDouble(1);
            } catch(SQLException e){
                AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                        "Wystąpił błąd podczas pobierania informacji o miesięcznej cenie subskrybcji.");
            }
            subskrybcja.setCenaZaMiesiac(cena);
            return subskrybcja;
        }catch(Exception e){
            AlertsUtil.showErrorAlert(null, "Błąd tworzenia obiektu",
                    "Wystąpił błąd podczas zapisu informacji pobranych z bazy danych.");
        }
        return null;
    }

    @Override
    public String getKey(Subskrybcja obj){
        return colNames[0] + obj.getDataRozpoczecia();
    }

    @Override
    public String getKeyValue(Subskrybcja obj) {
        return "" + obj.getDataRozpoczecia();
    }

    @Override
    public String getAttribForName(String name, Subskrybcja obj){
        return "null";
    }

    @Override
    protected String getInsertionValuesTemplate(Subskrybcja subskrybcja) {
        StringBuilder template = new StringBuilder("(");
        template.append(subskrybcja.getDataRozpoczecia()!=null?"?":"default").append(", ");
        template.append("?, ?, ?, ").append(subskrybcja.getIdAkcesorium()>0?"?":"null").append(", ");
        template.append("null)");
        return template.toString();
    }

    @Override
    protected String getIdentyficationTemplate() {
        return colNames[3] + "=? and " + colNames[1] + "=? and " + colNames[2] + "=? and " + colNames[0] + "=?";
    }

    @Override
    protected String getSearchParamsTemplate(Subskrybcja subskrybcja) {
        StringBuilder template = new StringBuilder();
        boolean first = true;
        if(subskrybcja.getDataRozpoczecia() != null){
            template.append(" where ").append(colNames[0]).append("=?");
            first = false;
        }
        if(subskrybcja.getKlientNrDowodu() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[1]).append("=?");
        }
        if(subskrybcja.getIdPrac() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[2]).append("=?");
        }
        if(subskrybcja.getIdRoweru() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[3]).append("=?");
        }
        if(subskrybcja.getIdAkcesorium() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[4]).append("=?");
        }
        if(subskrybcja.getDataZakonczenia() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[5]).append("=?");
        }
        return template.toString();
    }

    @Override
    public List<Subskrybcja> getWithPresetParams() {
        SearchParamsReady = false;
        return get(SearchObj);
    }

    @Override
    public void setSearchParams(Subskrybcja subskrybcja) {
        SearchObj = subskrybcja;
        SearchParamsReady = true;
    }

    @Override
    public boolean ifSerchParamsReady(){
        return SearchParamsReady;
    }

    @Override
    public List<Subskrybcja> getAll(){
        if(!ActiveOnly){
            return super.getAll();
        }
        else{
            String[] params = {"DATA_ZAKONCZENIA IS NULL"};
            return super.get(params);
        }
    }

    public static void setActiveOnly(boolean value){
        ActiveOnly = value;
    }
}
