package database;

import utils.AlertsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * class responsible for establishing and maintaining connection with the database
 */
public class DatabaseConnection {
    private static Connection connection;
    private static DatabaseConnection instance;

    private static final String DataBaseInfoFile = "/databaseInfo/databaseInfo.xml";

//    private static final int PortNum = 1521;
//    private static final String User = "inf141282";
//    private static final String Passwd = "inf141282";
//    private static final String ServiceName = "dblab02_students.cs.put.poznan.pl";
//    private static final String HostName = "admlab2.cs.put.poznan.pl";

    private static DatabaseInfoContainer databaseInfo;

    private static final String ConnCloseMessage = "Nie udało się poprawnie zamknąć połączenia: ";


    private DatabaseConnection(){
        databaseInfo = new DatabaseInfoContainer(DataBaseInfoFile);
        if(!databaseInfo.ifParseSuccess()) System.exit(-1);
        connect();
    }

    public Connection getConnection(){
        return connection;
    }

    /**
     * establishes connection with the database
     */
    private void connect(){
        String connectionString = "jdbc:oracle:thin:@//" + databaseInfo.getHostName() + ":" + databaseInfo.getPortNum()
                + "/" + databaseInfo.getServiceName();
        Properties connectionProps = new Properties();
        connectionProps.put("user", databaseInfo.getUser());
        connectionProps.put("password", databaseInfo.getPasswd());
        try {
            connection = DriverManager.getConnection(connectionString,
                    connectionProps);
        } catch (SQLException ex) {
            AlertsUtil.showErrorAlert(null, "Błąd połączenia",
                    "Nie udało się nawiązać połączenia z bazą danych.");
            System.exit(-1);
        }
    }

    /**
     * provides instance of DatabaseConnection class
     * if connection is established and not closed returns current connection class,
     * otherwise creates new connection
     * @return instance of DatabaseConnection class
     * @throws SQLException is thrown in case of error during connection establishing
     */
    public static DatabaseConnection getInstance() throws SQLException {
        if(instance==null){
            instance= new DatabaseConnection();
        }else if(instance.getConnection().isClosed()){
            instance = new DatabaseConnection();
        }

        return instance;
    }

    private static void clear() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ConnCloseMessage + ex.getMessage());
            AlertsUtil.showErrorAlert(null, "Błąd połączenia",
                    "Wystąpił błąd podczas zamykania połączenia z bazą danych.");
            System.exit(-1);
        }
    }

}
