package data.internal;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Querier {

    protected static final String ON_DUPLICATE_UPDATE = "ON DUPLICATE KEY UPDATE";

    protected Connection myConnection;

    public Querier(Connection connection) throws SQLException{
        myConnection = connection;
        prepareStatements();
    }

    protected abstract void prepareStatements() throws SQLException;

    protected abstract void closeStatements();
}
