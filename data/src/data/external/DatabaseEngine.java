package data.external;

import data.internal.AssetQuerier;
import data.internal.GameInformationQuerier;
import data.internal.Querier;
import data.internal.UserQuerier;

import java.sql.*;
import java.util.List;

public class DatabaseEngine {

    private static final String JDBC_DRIVER = "jdbc:mysql://";
    private static final String IP_ADDRESS = "67.159.94.60";
    private static final String PORT_NUMBER = "3306";
    private static final String DATABASE_NAME = "vooga_byteme";
    private static final String SERVER_TIMEZONE = "serverTimezone=UTC";
    private static final String DATABASE_URL = JDBC_DRIVER + IP_ADDRESS + ":" + PORT_NUMBER + "/" + DATABASE_NAME +
            "?" + SERVER_TIMEZONE;
    private static final String USERNAME = "vooga";
    private static final String PASSWORD = "byteMe!";

    private Connection myConnection;
    private GameInformationQuerier myGameInformationQuerier;
    private AssetQuerier myAssetQuerier;
    private UserQuerier myUserQuerier;
    private List<Querier> myQueriers;

    private static DatabaseEngine myInstance = new DatabaseEngine();

    private DatabaseEngine() {

    }

    public static DatabaseEngine getInstance() {
        return myInstance;
    }

    public boolean open() throws SQLException {
        try {
            myConnection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            initializeQueriers();
            return true;
        } catch (SQLException exception) {
            System.out.println("Couldn't connect to database" + exception.getMessage());
            return false;
        }
    }

    private void initializeQueriers() throws SQLException{
        myAssetQuerier = new AssetQuerier(myConnection);
        myGameInformationQuerier = new GameInformationQuerier(myConnection);
        myUserQuerier = new UserQuerier(myConnection);
        myQueriers = List.of(myAssetQuerier, myGameInformationQuerier, myUserQuerier);
    }

    public void close() {
        try {
            for (Querier querier : myQueriers){
                querier.closeStatements();
            }
            if (myConnection != null) {
                myConnection.close();
            }
        } catch (SQLException exception) {
            System.out.println("Couldn't close the connection");
        }
    }


}
