package database.daos;

import database.objects.Usterka;
import utils.AlertsUtil;

import java.sql.ResultSet;
import java.util.List;

public class UsterkaDao extends Dao<Usterka> {

    private static boolean SearchParamsReady = false;
    private static Usterka SearchObj = null;

    private static final String[] ColNames = {"data_zgloszenia", "data_naprawy", "pracownik_wpisujacy", "id_roweru",
            "opis", "pracownik_naprawiajacy"};
    private static final String TableName = "usterki";

    public UsterkaDao(){
        super(ColNames, TableName);
    }

    @Override
    protected Usterka createObject(ResultSet rs) {
        try{
            return new Usterka(rs.getDate(colNames[0]), rs.getDate(colNames[1]), rs.getLong(colNames[2]),
                    rs.getLong(colNames[3]), rs.getString(colNames[4]), rs.getLong(colNames[5]));
        }catch(Exception e){
            AlertsUtil.showErrorAlert(null, "Błąd tworzenia obiektu",
                    "Wystąpił błąd podczas zapisu informacji pobranych z bazy danych.");
        }
        return null;
    }

    @Override
    public List<Usterka> getWithPresetParams() {
        SearchParamsReady = false;
        return get(SearchObj);
    }

    @Override
    public String getAttribForName(String name, Usterka obj) {
        return null;
    }

    @Override
    public String getKey(Usterka obj) {
        return null;
    }

    @Override
    public String getKeyValue(Usterka obj) {
        return null;
    }

    @Override
    protected String getInsertionValuesTemplate(Usterka usterka) {
        StringBuilder template = new StringBuilder("(");
        template.append(usterka.getDataZgloszenia()!=null?"?":"default").append(", null, ?, ?, ");
        template.append(usterka.getOpis()!=null?"?":"null").append(", null)");
        return template.toString();
    }

    @Override
    protected String getIdentyficationTemplate() {
        return colNames[3] + "=? and " + colNames[2] + "=? and " + colNames[0] + "=?";
    }

    @Override
    protected String getSearchParamsTemplate(Usterka usterka) {
        StringBuilder template = new StringBuilder();
        boolean first = true;
        if(usterka.getDataZgloszenia() != null){
            template.append(" where ").append(colNames[0]).append("=?");
            first = false;
        }
        if(usterka.getDataNaprawy() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[1]).append("=?");
        }
        if(usterka.getPracownikWpisujacy() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
                first = false;
            }
            template.append(colNames[2]).append("=?");
        }
        if(usterka.getIdRoweru() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[3]).append("=?");
        }
        if(usterka.getOpis() != null){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[4]).append("=?");
        }
        if(usterka.getPracownikNaprawiajacy() > 0){
            if(!first) template.append(" and ");
            else{
                template.append(" where ");
            }
            template.append(colNames[5]).append("=?");
        }
        return template.toString();
    }

    @Override
    public void setSearchParams(Usterka usterka) {
        SearchObj = usterka;
        SearchParamsReady = true;
    }

    @Override
    public boolean ifSerchParamsReady() {
        return SearchParamsReady;
    }
}
