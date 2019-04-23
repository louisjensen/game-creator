package data.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameInformationQuerier extends Querier {

    private static final String GAME_INFORMATION_TABLE_NAME = "GameInformation";
    private static final String GAME_NAME_COLUMN = "GameName";
    private static final String GAME_DATA_COLUMN = "GameData";
    private static final String GAME_INFO_COLUMN = "GameInfo";
    private static final String AUTHOR_NAME_COLUMN = "AuthorName";

    private static final String GAME_DATA_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", GAME_INFORMATION_TABLE_NAME, GAME_NAME_COLUMN, AUTHOR_NAME_COLUMN, GAME_DATA_COLUMN);
    private static final String GAME_INFO_INSERT = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", GAME_INFORMATION_TABLE_NAME, GAME_NAME_COLUMN, AUTHOR_NAME_COLUMN, GAME_INFO_COLUMN);
    private static final String UPDATE_GAME_DATA =
            String.format("%s %s %s = ?", GAME_DATA_INSERT, ON_DUPLICATE_UPDATE, GAME_DATA_COLUMN);
    private static final String UPDATE_GAME_INFO =
            String.format("%s %s %s = ?", GAME_INFO_INSERT, ON_DUPLICATE_UPDATE, GAME_INFO_COLUMN);
    private static final String LOAD_GAME_DATA =
            String.format("SELECT %s FROM %s WHERE %s = ?;", GAME_DATA_COLUMN, GAME_INFORMATION_TABLE_NAME, GAME_NAME_COLUMN);
    private static final String LOAD_GAME_INFORMATION =
            String.format("SELECT %s FROM %s WHERE %s = ? AND %s IS NOT NULL;", GAME_INFO_COLUMN, GAME_INFORMATION_TABLE_NAME, GAME_NAME_COLUMN, GAME_INFO_COLUMN);
    private static final String FIND_ALL_GAME_NAMES =
            String.format("SELECT DISTINCT(%s) FROM %s;", GAME_NAME_COLUMN, GAME_INFORMATION_TABLE_NAME);

    private PreparedStatement myUpdateGameEntryDataStatement;
    private PreparedStatement myUpdateGameEntryInfoStatement;
    private PreparedStatement myLoadGameDataStatement;
    private PreparedStatement myLoadGameInformationStatement;
    private PreparedStatement myFindAllGameNamesStatement;

    public GameInformationQuerier(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    protected void prepareStatements() throws SQLException {
        myUpdateGameEntryDataStatement = myConnection.prepareStatement(UPDATE_GAME_DATA);
        myUpdateGameEntryInfoStatement =  myConnection.prepareStatement(UPDATE_GAME_INFO);
        myLoadGameDataStatement = myConnection.prepareStatement(LOAD_GAME_DATA);
        myLoadGameInformationStatement = myConnection.prepareStatement(LOAD_GAME_INFORMATION);
        myFindAllGameNamesStatement = myConnection.prepareStatement(FIND_ALL_GAME_NAMES);
        myPreparedStatements = List.of(myUpdateGameEntryDataStatement, myUpdateGameEntryInfoStatement,
                myLoadGameDataStatement, myLoadGameInformationStatement, myFindAllGameNamesStatement);
    }


//    public void updateGameEntryInfo(String gameName, String rawXML) throws SQLException{
//        updateGameEntryInfo(gameName, DEFAULT_AUTHOR, rawXML);
//    }

    public String loadGameData(String gameName) throws SQLException{
        return loadXML(gameName, myLoadGameDataStatement, GAME_DATA_COLUMN);
    }

    private String loadGameInformation(String gameName) throws SQLException{
        return loadXML(gameName, myLoadGameInformationStatement, GAME_INFO_COLUMN);
    }

    public List<String> loadAllGameInformationXMLs() throws SQLException{
        List<String> gameInformations = new ArrayList<>();
        List<String> gameNames = getGameNames();
        for (String game : gameNames){
            String gameInfoXML = loadGameInformation(game);
            if (gameInfoXML != null){
                gameInformations.add(gameInfoXML);
            }
        }
        return gameInformations;
    }

    private List<String> getGameNames() throws SQLException{
        List<String> gameNames = new ArrayList<>();
        ResultSet resultSet = myFindAllGameNamesStatement.executeQuery();
        while (resultSet.next()){
            gameNames.add(resultSet.getString(GAME_NAME_COLUMN));
        }
        return gameNames;
    }

    private String loadXML(String gameName, PreparedStatement preparedStatement, String columnName) throws SQLException {
        preparedStatement.setString(1, gameName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(columnName);
        }
        return null;
    }

    public void updateGameEntryData(String gameName, String authorName, String myRawXML) throws SQLException{
        prepareAndExecuteUpdate(myUpdateGameEntryDataStatement, gameName, authorName, myRawXML);
    }

    private void prepareAndExecuteUpdate(PreparedStatement statement, String gameName, String authorName,
                                         String myRawXML) throws SQLException {
        statement.setString(1, gameName);
        statement.setString(2, authorName);
        statement.setString(3, myRawXML);
        statement.setString(4, myRawXML);
        statement.executeUpdate();
    }

    public void updateGameEntryInfo(String gameName, String authorName, String myRawXML) throws SQLException{
        prepareAndExecuteUpdate(myUpdateGameEntryInfoStatement, gameName, authorName, myRawXML);
    }
}
