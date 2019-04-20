package data.external;

import data.internal.ResultsProcessor;
import data.internal.TablePrinter;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseEngine {

    public static final String TABLE_USER_AUTHENTICATION = "user_authentication";

    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "hashed_password";
    public static final String COLUMN_EMAIL = "email";

    private static final String JDBC_DRIVER = "jdbc:mysql://";
    private static final String IP_ADDRESS = "67.159.94.60";
    private static final String PORT_NUMBER = "3306";
    private static final String DATABASE_NAME = "vooga_byteme";
    private static final String SERVER_TIMEZONE = "serverTimezone=UTC";
    private static final String DATABASE_URL = JDBC_DRIVER + IP_ADDRESS + ":"+ PORT_NUMBER + "/" + DATABASE_NAME +
            "?" + SERVER_TIMEZONE;
    private static final String USERNAME = "vooga";
    private static final String PASSWORD = "byteMe!";

    public static final String GAME_INFORMATION_TABLE_NAME = "GameInformation";
    private static final String GAME_ID_COLUMN = "GameID";
    private static final String GAME_NAME_COLUMN = "GameName";
    private static final String GAME_DATA_COLUMN = "GameData";
    private static final String GAME_INFO_COLUMN = "GameInfo";
    private static final String ASSETS_COLUMN = "Assets";
    private static final String FIRST_PUBLISHED_COLUMN = "FirstPublished";
    private static final String MOST_RECENT_PUBLISHED_COLUMN = "MostRecentPublish";
    private static final String AUTHOR_NAME_COLUMN = "AuthorName";
    public static final List<String> GAME_INFORMATION_COLUMN_NAMES = List.of(
            GAME_NAME_COLUMN,
            GAME_DATA_COLUMN,
            GAME_INFO_COLUMN,
            ASSETS_COLUMN,
            FIRST_PUBLISHED_COLUMN,
            MOST_RECENT_PUBLISHED_COLUMN,
            AUTHOR_NAME_COLUMN
    );

    public static final String GAME_STATISTICS_TABLE_NAME = "GameStatistics";
    private static final String USER_ID_COLUMN = "UserID";
    private static final String SCORE_COLUMN = "Score";
    private static final String TIMESTAMP_COLUMN = "Timestamp";
    public static final List<String> GAME_STATISTICS_COLUMN_NAMES = List.of(
            GAME_ID_COLUMN,
            GAME_NAME_COLUMN,
            USER_ID_COLUMN,
            SCORE_COLUMN,
            TIMESTAMP_COLUMN
    );

    public static final String USERS_TABLE_NAME= "Users";
    private static final String USERNAME_COLUMN = "UserName";
    private static final String PASSWORD_COLUMN = "Password";
    private static final String AUTHORED_GAMES_COLUMN = "AuthoredGames";
    private static final String LAST_LOGIN_COLUMN = "LastLogin";
    private static final String FIRST_LOGIN_COLUMN = "FirstLogin";
    public static final List<String> USERS_COLUMN_NAMES = List.of(
            USERNAME_COLUMN,
            USER_ID_COLUMN,
            PASSWORD_COLUMN,
            AUTHORED_GAMES_COLUMN,
            LAST_LOGIN_COLUMN,
            FIRST_LOGIN_COLUMN
    );

    public static final String CHECKPOINTS_TABLE_NAME = "Checkpoints";
    private static final String LEVEL_OBJECT_COLUMN = "LevelObjectXML";
    private static final String CHECKPOINT_COLUMN = "CheckpointTime";
    public static final List<String> CHECKPOINTS_COLUMN_NAMES = List.of(
            USERNAME_COLUMN,
            USER_ID_COLUMN,
            GAME_NAME_COLUMN,
            LEVEL_OBJECT_COLUMN,
            CHECKPOINT_COLUMN
    );

    public static final String IMAGES_TABLE_NAME = "Images";
    public static final String IMAGE_NAME_COLUMN = "ImageName";
    public static final String IMAGE_DATA_COLUMN = "ImageData";
    public static final List<String> IMAGES_COLUMN_NAMES = List.of(
            IMAGE_NAME_COLUMN,
            IMAGE_DATA_COLUMN
    );

    public static final String SOUNDS_TABLE_NAME = "Sounds";
    public static final String SOUND_NAME_COLUMN = "SoundName";
    public static final String SOUND_DATA_COLUMN = "SoundData";
    public static final List<String> SOUNDS_COLUMN_NAMES = List.of(
            SOUND_NAME_COLUMN,
            SOUND_DATA_COLUMN
    );

    public static final Map<String, List<String>> DATABASE_SCHEMA = Map.of(
            GAME_INFORMATION_TABLE_NAME, GAME_INFORMATION_COLUMN_NAMES,
            GAME_STATISTICS_TABLE_NAME, GAME_STATISTICS_COLUMN_NAMES,
            USERS_TABLE_NAME, USERS_COLUMN_NAMES,
            CHECKPOINTS_TABLE_NAME, CHECKPOINTS_COLUMN_NAMES,
            IMAGES_TABLE_NAME, IMAGES_COLUMN_NAMES,
            SOUNDS_TABLE_NAME, SOUNDS_COLUMN_NAMES
    );

    private static final String CREATE_GAME_ENTRY =
            "INSERT INTO "+ GAME_INFORMATION_TABLE_NAME + " (" + GAME_NAME_COLUMN + ") VALUES" +
        "(?);";
    private static final String UPDATE_GAME_DATA =
            "UPDATE " + GAME_INFORMATION_TABLE_NAME + " SET " + GAME_DATA_COLUMN + " = ? WHERE " + GAME_NAME_COLUMN + " = ?;";
    private static final String UPDATE_GAME_INFO = "UPDATE " + GAME_INFORMATION_TABLE_NAME + " SET " + GAME_INFO_COLUMN + " = ? " +
            "WHERE " + GAME_NAME_COLUMN + " = ?;";
    private static final String LOAD_GAME_DATA =
            "SELECT " + GAME_DATA_COLUMN + " FROM " + GAME_INFORMATION_TABLE_NAME + " WHERE " + GAME_NAME_COLUMN + " " +
                    "= ?;";
    private static final String LOAD_GAME_INFORMATION =
            "SELECT " + GAME_INFO_COLUMN + " FROM " + GAME_INFORMATION_TABLE_NAME + " WHERE " + GAME_NAME_COLUMN + " " +
                    "= ? AND " + GAME_INFO_COLUMN + " IS NOT NULL;";
    private static final String FIND_ALL_GAME_NAMES =
            "SELECT DISTINCT(" + GAME_NAME_COLUMN + ") FROM " + GAME_INFORMATION_TABLE_NAME + ";";

    private Connection myConnection;
    private PreparedStatement myCreateGameEntryStatement;
    private PreparedStatement myUpdateGameEntryDataStatement;
    private PreparedStatement myUpdateGameEntryInfoStatement;
    private PreparedStatement myLoadGameDataStatement;
    private PreparedStatement myLoadGameInformationStatement;
    private PreparedStatement myFindAllGameNamesStatement;

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
            myCreateGameEntryStatement = myConnection.prepareStatement(CREATE_GAME_ENTRY);
            myUpdateGameEntryDataStatement = myConnection.prepareStatement(UPDATE_GAME_DATA);
            myUpdateGameEntryInfoStatement =  myConnection.prepareStatement(UPDATE_GAME_INFO);
            myLoadGameDataStatement = myConnection.prepareStatement(LOAD_GAME_DATA);
            myLoadGameInformationStatement = myConnection.prepareStatement(LOAD_GAME_INFORMATION);
            myFindAllGameNamesStatement = myConnection.prepareStatement(FIND_ALL_GAME_NAMES);

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

    public void createEntryForNewGame(String gameName) throws SQLException{
        myCreateGameEntryStatement.setString(1, gameName);
        myCreateGameEntryStatement.execute();
    }

    public void printGameTable(){
        try {
            printTable(GAME_INFORMATION_TABLE_NAME);
        } catch (SQLException e) {
            System.out.println("Failed to print table: " + e.getMessage());
        }
    }

    public void printTable(String tableToPrint) throws SQLException{
        executeQuery("SELECT * FROM " + tableToPrint, new TablePrinter(tableToPrint));
    }

    private void executeStatement (String sqlStatement){
        try (Statement statement = myConnection.createStatement()){
            statement.execute(sqlStatement);
        } catch (SQLException exception){
            System.out.println("Statement failed: " + exception.getMessage());
        }
    }

    private void executeQuery(String sqlQuery, ResultsProcessor resultsProcessor) {
        open();
        try (Statement statement = myConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)){
            resultsProcessor.processResults(resultSet);
        } catch (SQLException exception){
            System.out.println("Query failed: " + exception.getMessage());
        }
        close();
    }

    private String executeQuery(String sqlQuery, String columnName){
        try (Statement statement = myConnection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)){
            if (resultSet != null && resultSet.next()){
                return resultSet.getString(columnName);
            }
        } catch (SQLException exception){
            System.out.println("Query failed: " + exception.getMessage());
        }
        return  null;
    }

    public void updateGameEntryData(String gameName, String rawXML) throws SQLException{
        myUpdateGameEntryDataStatement.setString(1, rawXML);
        myUpdateGameEntryDataStatement.setString(2, gameName);
        myUpdateGameEntryDataStatement.execute();
    }

    public void updateGameEntryInfo(String gameName, String rawXML) throws SQLException{
        myUpdateGameEntryInfoStatement.setString(1, rawXML);
        myUpdateGameEntryInfoStatement.setString(2, gameName);
        myUpdateGameEntryInfoStatement.execute();
    }

    public void addAssets(String gameName, List<String> assetPaths){

    }

    public String loadGameData(String gameName) throws SQLException{
        return loadXML(gameName, myLoadGameDataStatement, GAME_DATA_COLUMN);
    }

    public String loadGameInformation(String gameName) throws SQLException{
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

    private void printResults(ResultSet results) throws SQLException {
        // iterate of the results, starts at beginning so results.next() takes you to first record
        while (results.next()){
            System.out.println(results.getString(1));
//            System.out.println(results.getString("name") + " " +
//                    results.getInt("phone") + " " +
//                    results.getString("email"));
        }
        // have to close your results sets since it is a resource
        results.close();
    }


    public void saveImage(String imageName, File imageToSave) {
        saveAsset(imageName, imageToSave, "INSERT INTO Images (ImageName, ImageData) VALUES (?, ?)");
    }

    public void saveSound(String soundName, File soundToSave){
        saveAsset(soundName, soundToSave, "INSERT INTO Sounds (SoundName, SoundData) VALUES (?, ?)");
    }

    public InputStream loadImage(String imageName){
        return loadAsset(imageName, IMAGE_DATA_COLUMN,"SELECT ImageData FROM Images WHERE ImageName = ?");
    }

    public InputStream loadSound(String soundName){
        return loadAsset(soundName, SOUND_DATA_COLUMN,"SELECT SoundData FROM Sounds WHERE SoundName = ?");
    }

    private InputStream loadAsset(String assetName, String columnName, String sqlQuery) {
        try (PreparedStatement statement = myConnection.prepareStatement(sqlQuery)){
            statement.setString(1, assetName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                InputStream imageInputStream = resultSet.getBinaryStream(columnName);
                resultSet.close();
                return  imageInputStream;
            }
        } catch (SQLException e) {
            System.out.println("Could not load asset: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void saveAsset (String assetName, File assetToSave, String sqlQuery) {
        try (PreparedStatement statement = myConnection.prepareStatement(sqlQuery)){
            statement.setString(1, assetName);
            statement.setBinaryStream(2, new BufferedInputStream(new FileInputStream(assetToSave)));
            statement.execute();
        } catch (SQLException exception){
            System.out.println("Statement failed: " + exception.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file: " + assetToSave.toString());
        }
    }
}
