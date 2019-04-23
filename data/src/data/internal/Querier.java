package data.internal;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Querier {

    protected Connection myConnection;

    public Querier(Connection connection) throws SQLException{
        myConnection = connection;
        prepareStatements();
    }

    protected abstract void prepareStatements() throws SQLException;

    protected abstract void closeStatements();
}
