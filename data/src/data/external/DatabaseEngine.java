package data.external;

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

    private static final String ON_DUPLICATE_UPDATE = "ON DUPLICATE KEY UPDATE";
    private static final String GAME_DATA_INSERT = "INSERT INTO "+ GAME_INFORMATION_TABLE_NAME +
            " (" + GAME_NAME_COLUMN + ", " + AUTHOR_NAME_COLUMN + ", " + GAME_DATA_COLUMN + ") VALUES (?, ?, ?)";
    private static final String GAME_INFO_INSERT = "INSERT INTO "+ GAME_INFORMATION_TABLE_NAME + " (" +
            GAME_NAME_COLUMN + ", " + AUTHOR_NAME_COLUMN + ", " + GAME_INFO_COLUMN + ") VALUES (?, ?, ?)";
    private static final String CREATE_GAME_ENTRY = "INSERT INTO "+ GAME_INFORMATION_TABLE_NAME + " (" +
            GAME_NAME_COLUMN + ") VALUES" + "(?);";
    private static final String UPDATE_GAME_DATA =
            GAME_DATA_INSERT + " " + ON_DUPLICATE_UPDATE + " " + GAME_DATA_COLUMN + " = ?";
    private static final String UPDATE_GAME_INFO =
            GAME_INFO_INSERT + " " + ON_DUPLICATE_UPDATE + " " + GAME_INFO_COLUMN + " = ?";
    private static final String LOAD_GAME_DATA =
            "SELECT " + GAME_DATA_COLUMN + " FROM " + GAME_INFORMATION_TABLE_NAME + " WHERE " + GAME_NAME_COLUMN + " " +
                    "= ?;";
    private static final String LOAD_GAME_INFORMATION =
            "SELECT " + GAME_INFO_COLUMN + " FROM " + GAME_INFORMATION_TABLE_NAME + " WHERE " + GAME_NAME_COLUMN + " " +
                    "= ? AND " + GAME_INFO_COLUMN + " IS NOT NULL;";
    private static final String FIND_ALL_GAME_NAMES =
            "SELECT DISTINCT(" + GAME_NAME_COLUMN + ") FROM " + GAME_INFORMATION_TABLE_NAME + ";";
    private static final String IMAGES_INSERT = "INSERT INTO " + IMAGES_TABLE_NAME + " (" + IMAGE_NAME_COLUMN + ", " +
        IMAGE_DATA_COLUMN + ") VALUES (?, ?)";
    private static final String SOUNDS_INSERT = "INSERT INTO " + SOUNDS_TABLE_NAME + " (" + SOUND_NAME_COLUMN + ", " +
            SOUND_DATA_COLUMN + ") VALUES (?, ?)";
    private static final String UPDATE_IMAGES =
            IMAGES_INSERT + " " + ON_DUPLICATE_UPDATE + " " + IMAGE_DATA_COLUMN + " = ?";
    private static final String UPDATE_SOUNDS =
            SOUNDS_INSERT + " " + ON_DUPLICATE_UPDATE + " " + SOUND_DATA_COLUMN + " = ?";
    private static final String LOAD_SOUND =
            "SELECT " + SOUND_DATA_COLUMN + " FROM " + SOUNDS_TABLE_NAME + " WHERE " + SOUND_NAME_COLUMN + " = ?";
    private static final String LOAD_IMAGE =
            "SELECT " + IMAGE_DATA_COLUMN + " FROM " + IMAGES_TABLE_NAME + " WHERE " + IMAGE_NAME_COLUMN + " = ?";
    private static final String GET_HASHED_PASSWORD = "SELECT " + PASSWORD_COLUMN + " FROM " + USERS_TABLE_NAME + " " +
            "WHERE " + USERNAME_COLUMN + " = ?";
    private static final String CREATE_USER =
            "INSERT INTO " + USERS_TABLE_NAME + " (" + USERNAME_COLUMN + ", " + PASSWORD_COLUMN + ") VALUES (?, ?)";


    public static final String DEFAULT_AUTHOR = "DefaultAuthor";
    public static final String HASH_ALGORITHM = "SHA-256";

    private Connection myConnection;
    private PreparedStatement myUpdateGameEntryDataStatement;
    private PreparedStatement myUpdateGameEntryInfoStatement;
    private PreparedStatement myLoadGameDataStatement;
    private PreparedStatement myLoadGameInformationStatement;
    private PreparedStatement myFindAllGameNamesStatement;
    private PreparedStatement myUpdateImagesStatement;
    private PreparedStatement myUpdateSoundsStatement;
    private PreparedStatement myLoadImageStatement;
    private PreparedStatement myLoadSoundStatement;
    private PreparedStatement myGetPasswordStatement;
    private PreparedStatement myCreateUserStatement;

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
            myUpdateGameEntryDataStatement = myConnection.prepareStatement(UPDATE_GAME_DATA);
            myUpdateGameEntryInfoStatement =  myConnection.prepareStatement(UPDATE_GAME_INFO);
            myLoadGameDataStatement = myConnection.prepareStatement(LOAD_GAME_DATA);
            myLoadGameInformationStatement = myConnection.prepareStatement(LOAD_GAME_INFORMATION);
            myFindAllGameNamesStatement = myConnection.prepareStatement(FIND_ALL_GAME_NAMES);
            myUpdateImagesStatement = myConnection.prepareStatement(UPDATE_IMAGES);
            myUpdateSoundsStatement = myConnection.prepareStatement(UPDATE_SOUNDS);
            myLoadImageStatement = myConnection.prepareStatement(LOAD_IMAGE);
            myLoadSoundStatement = myConnection.prepareStatement(LOAD_SOUND);
            myGetPasswordStatement = myConnection.prepareStatement(GET_HASHED_PASSWORD);
            myCreateUserStatement = myConnection.prepareStatement(CREATE_USER);
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

    public void updateGameEntryInfo(String gameName, String rawXML) throws SQLException{
        updateGameEntryInfo(gameName, DEFAULT_AUTHOR, rawXML);
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

    public void saveImage(String imageName, File imageToSave) {
        saveAsset(imageName, imageToSave, myUpdateImagesStatement);
    }

    public void saveSound(String soundName, File soundToSave){
        saveAsset(soundName, soundToSave, myUpdateSoundsStatement);
    }

    public InputStream loadImage(String imageName){
        return loadAsset(imageName, IMAGE_DATA_COLUMN, myLoadImageStatement);
    }

    public InputStream loadSound(String soundName){
        return loadAsset(soundName, SOUND_DATA_COLUMN, myLoadSoundStatement);
    }

    private InputStream loadAsset(String assetName, String columnName, PreparedStatement statement) {
        try {
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

    private void saveAsset (String assetName, File assetToSave, PreparedStatement statement) {
        try {
            BufferedInputStream assetData = new BufferedInputStream(new FileInputStream(assetToSave));
            System.out.println(assetData);
            statement.setString(1, assetName);
            statement.setBinaryStream(2, new BufferedInputStream(new FileInputStream(assetToSave)));
            statement.setBinaryStream(3, new BufferedInputStream(new FileInputStream(assetToSave)));
            statement.executeUpdate();
        } catch (SQLException e){
            System.out.println("Could not save the asset: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file: " + assetToSave.toString());
        }
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

    public boolean authenticateUser(String userName, String password) {
        try {
            String hashedPassword = generateHashedPassword(password);
            String storedHash = retrieveStoredHash(userName);
            return hashedPassword.equals(storedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Could not validate user: " + e.getMessage());
        }
        return false;
    }

    public boolean createUser(String userName, String password) throws SQLException{
        myCreateUserStatement.setString(1, userName);
        try {
            myCreateUserStatement.setString(2, generateHashedPassword(password));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not generate hash: " + e.getMessage());
        }
        int updates = myCreateUserStatement.executeUpdate();
        return updates == 1;
    }

    private String retrieveStoredHash(String userName) throws SQLException{
        myGetPasswordStatement.setString(1, userName);
        ResultSet resultSet = myGetPasswordStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(PASSWORD_COLUMN);
        } else {
            return "";
        }
    }

    private String generateHashedPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        byte[] bytes = messageDigest.digest(password.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++){
            stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBuilder.toString();
    }
}
