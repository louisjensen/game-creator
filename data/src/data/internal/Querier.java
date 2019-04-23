package data.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class Querier {

    protected static final String ON_DUPLICATE_UPDATE = "ON DUPLICATE KEY UPDATE";

    protected Connection myConnection;
    protected List<PreparedStatement> myPreparedStatements;

    public Querier(Connection connection) throws SQLException{
        myConnection = connection;
        prepareStatements();
    }

    protected abstract void prepareStatements() throws SQLException;

    public void closeStatements() throws SQLException{
        for (PreparedStatement statement : myPreparedStatements){
            if (statement != null){
                statement.close();
            }
        }
    }
}
