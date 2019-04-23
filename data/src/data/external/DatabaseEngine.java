package data.external;

import data.internal.UserQuerier;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseEngine {

    private static final String JDBC_DRIVER = "jdbc:mysql://";
    private static final String IP_ADDRESS = "67.159.94.60";
    private static final String PORT_NUMBER = "3306";
    private static final String DATABASE_NAME = "vooga_byteme";
    private static final String SERVER_TIMEZONE = "serverTimezone=UTC";
    private static final String DATABASE_URL = JDBC_DRIVER + IP_ADDRESS + ":"+ PORT_NUMBER + "/" + DATABASE_NAME +
            "?" + SERVER_TIMEZONE;
    private static final String USERNAME = "vooga";
    private static final String PASSWORD = "byteMe!";

    private Connection myConnection;

    private static DatabaseEngine myInstance = new DatabaseEngine();

    private DatabaseEngine(){

    }

    public static DatabaseEngine getInstance(){
        return myInstance;
    }

    public boolean open() {
        try {
            myConnection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            initializePreparedStatements();
            return true;
        } catch (SQLException exception){
            System.out.println("Couldn't connect to database" + exception.getMessage());
            exception.printStackTrace();
            return false;
        }
    }

    private void initializePreparedStatements() {
        try {

        } catch (SQLException exception){
            System.out.println("Statement Preparation Failed " + exception.getMessage());
        }
    }

    public void close() {
        try {
            if (myConnection != null){
                myConnection.close();
            }
        } catch (SQLException exception){
            System.out.println("Couldn't close the connection");
        }
    }



}
