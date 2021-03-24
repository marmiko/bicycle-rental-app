package database.daos;

import database.DatabaseConnection;
import database.objects.Statementable;
import utils.AlertsUtil;
import utils.ErrorAlertsManager;

import java.sql.*;
import java.util.*;

/**
 * Database Access Object class
 * @param <T> class type of the Java representation of database objects accessed by the instance of Dao
 */
public abstract class Dao<T> {

    protected String[] colNames;
    protected String tableName;

    public Dao(String[] colNames, String tableNames){
        this.colNames = colNames;
        this.tableName = tableNames;
    }

    /**
     * creates instance of class T
     * @param rs contains attributes values (retrieved from the database) for the new T class object
     * @return created instance of class T
     */
    protected abstract T createObject(ResultSet rs);

    /**
     * performs selection of data of an object having provided id from the database
     * @param id object's id
     * @return if object found in the database then instance of T with its data,
     * otherwise Optional object with no contents
     */
    public Optional<T> get(long id){
        Connection con;
        Statement stmt =null;
        ResultSet rs =null;
        Optional<T> result = Optional.empty();
        try {
            con = DatabaseConnection.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from " + tableName + " where " + colNames[0] + "=" + id);

                    result = Optional.ofNullable(createObject(rs));

        } catch (SQLException ex) {
            AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                    "Wystąpił błąd podczas pobierania informacji z bazy danych.");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                            "Wystąpił błąd podczas kończenia odczytu z bazy danych.");
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                            "Wystąpił błąd podczas kończenia odczytu z bazy danych.");
                }
            }
        }
        return result;
    }

    /**
     * retrieves from the database data of all objects corresponding to the type T
     * @return List of T instances representing database objects
     */
    public List<T> getAll(){
        Connection con;
        Statement stmt =null;
        ResultSet rs =null;
        List<T> result = new ArrayList<>();

        try {
            con = DatabaseConnection.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from " + tableName);

            while(rs.next()){
                result.add(createObject(rs));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                            "Wystąpił błąd podczas kończenia odczytu z bazy danych.");
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                            "Wystąpił błąd podczas kończenia odczytu z bazy danych.");
                }
            }
        }
        return result;
    }

    /**
     * retrieves from the database data of all objects corresponding to the type T and fitting given conditions
     * @param t object representing conditions to be met; in the format of SQL condition, ie. attribName='varchar2AttribValue'
     * @return List of T instances representing database objects
     */
    public List<T> get(T t){
        Connection con;
        PreparedStatement stmt =null;
        ResultSet rs =null;
        StringBuilder polecenie = new StringBuilder("select * from " + tableName);
        polecenie.append(getSearchParamsTemplate(t));
        List<T> result = new ArrayList<>();
        try {
            con = DatabaseConnection.getInstance().getConnection();
            stmt = con.prepareStatement(polecenie.toString());
            Statementable<T> tS = (Statementable<T>) t;
            tS.prepareSearchStatement(stmt);
            rs = stmt.executeQuery();
            while(rs.next()){
                result.add(createObject(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                            "Wystąpił błąd podczas kończenia odczytu z bazy danych.");
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                            "Wystąpił błąd podczas kończenia odczytu z bazy danych.");
                }
            }
        }
        return result;
    }

    public List<T> get(String[] params){
        Connection con;
        Statement stmt =null;
        ResultSet rs =null;
        int i;
        StringBuilder polecenie = new StringBuilder("select * from " + tableName + " where ");

        for(i = 0; i < params.length; i++){
            if(i>1) polecenie.append(" and  ");
            polecenie.append(params[i]);
        }
        List<T> result = new ArrayList<>();
        try {
            con = DatabaseConnection.getInstance().getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery(polecenie.toString());
            while(rs.next()){
                result.add(createObject(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                            "Wystąpił błąd podczas kończenia odczytu z bazy danych.");
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    AlertsUtil.showErrorAlert(null, "Błąd pobierania danych",
                            "Wystąpił błąd podczas kończenia odczytu z bazy danych.");
                }
            }
        }
        return result;
    }

    public abstract List<T> getWithPresetParams();

    /**
     * inserts new object corresponding to type T to the database
     * @param t object to be inserted
     */
    public void insert(T t){
        StringBuilder polecenie = new StringBuilder("insert into ");
        polecenie.append(tableName).append(" values ");
        polecenie.append(getInsertionValuesTemplate(t));
        try{
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(polecenie.toString());
            Statementable<T> tIns = (Statementable<T>) t;
            stmt = tIns.prepareInsertStatement(stmt);
            executeUpdate(stmt, "Obiekt nie został dodany");
        } catch (SQLException e){
            AlertsUtil.showErrorAlert(null, "Błąd wstawiania danych",
                    "Wystąpił błąd podczas wstawiania obiektu do bazy danych.");
        }
    }

    public void update(T t, T oldT){
        StringBuilder polecenie = new StringBuilder();
        polecenie.append("update ").append(tableName).append(" set ");
        polecenie.append(getModificationParametersTemplate());
        polecenie.append(" where ").append(getIdentyficationTemplate());
        try{
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(polecenie.toString());
            Statementable<T> tIns = (Statementable<T>) t;
            stmt = tIns.prepareModifyStatement(stmt, oldT);
            executeUpdate(stmt, "Obiekt nie został zmodyfikowany");
        } catch (SQLException e){
            AlertsUtil.showErrorAlert(null, "Błąd aktualizacji danych",
                    "Wystąpił błąd podczas aktualizacji informacji w bazie danych.");
        }
    }

    /**
     * deletes object corresponding to the provided one from the database
     * @param t object to be removed
     */
    public void delete(T t){
        StringBuilder polecenie = new StringBuilder("delete from ");
        polecenie.append(tableName);
        polecenie.append(" where ");
        polecenie.append(getIdentyficationTemplate());
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(polecenie.toString());
            Statementable tIns = (Statementable) t;
            stmt = tIns.prepareIdentifiedStatement(stmt);
            executeUpdate(stmt, "Obiekt nie został usunięty");
        }catch (SQLException e){
            AlertsUtil.showErrorAlert(null, "Błąd usuwania danych",
                    "Wystąpił błąd podczas usuwania obiektów z bazy danych.");
        }
    }

    /**
     * getter for an attribute of a given name
     * @param name attribute name
     * @param obj class T instance that shall provide value of the attrib
     * @return value converted to String
     */
    public abstract String getAttribForName(String name, T obj);

    /**
     * provides SQL identifying key of a T object
     * @param obj object providing identification
     * @return key in the SQL format, ie. key='keyValue'
     */
    public abstract String getKey(T obj);

    /**
     * provides value of the SQL identifying key of a T object
     * @param obj object providing identification
     * @return value of the key converted to String
     */
    public abstract String getKeyValue(T obj);

    /**
     * performs update operation on the database
     * @param stmt SQL PreparedStatement to be executed
     * @param errorMsg message to be printed in the case of failure
     */
    private void executeUpdate(PreparedStatement stmt, String errorMsg){
        try {
            boolean error = stmt.execute();
            if (error){
                System.err.println(errorMsg);
            }
        } catch (SQLException e) {
            ErrorAlertsManager.displayError(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    AlertsUtil.showErrorAlert(null, "Błąd operacji",
                            "Wystąpił błąd podczas kończenia operacji na bazie danych.");
                }
            }
        }
    }

    protected abstract String getInsertionValuesTemplate(T t);

    protected abstract String getIdentyficationTemplate();

    protected abstract String getSearchParamsTemplate(T t);

    private String getModificationParametersTemplate(){
        StringBuilder template = new StringBuilder();
        template.append(colNames[0]).append("=?");
        for(int i = 1; i<colNames.length; i++){
            template.append(", ").append(colNames[i]).append("=?");
        }
        return template.toString();
    }

    public abstract void setSearchParams(T t);

    public abstract boolean ifSerchParamsReady();

}
