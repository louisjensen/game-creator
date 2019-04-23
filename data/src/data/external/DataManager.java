package data.external;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DataManager is the wrapper class that allows other modules to interact with the database, saving and loading
 * games, images, and user information.  Whenever another module wants to access the database, it should use a
 * DataManager object and call the appropriate method
 */
public class DataManager implements ExternalData{

    private static final String XML_EXTENSION = ".xml";
    private static final String GAME_INFO = "game_info";
    private static final String DEFAULT_AUTHOR = "DefaultAuthor";

    private XStream mySerializer;
    private DatabaseEngine myDatabaseEngine;

    /**
     * DataManager constructor creates a new serializer and connects to the the Database
     */
    public DataManager(){
        mySerializer = new XStream(new DomDriver());
        myDatabaseEngine = DatabaseEngine.getInstance();
    }

    /**
     * createGameFolder is a deprecated method that used to be necessary for creating a folder for the new game. Now
     * it is unnecessary to create a folder at all seeing as the database is being used
     * @param folderName name of the folder for the game to be saved in
     */
    @Deprecated
    public void createGameFolder(String folderName) {
        //Method is no longer necessary for saving games, using saveGameData will suffice
    }

    /**
     * Saves an object passed to the method to an xml file at the specified path
     * @param path to the file to be saved
     * @param objectToBeSaved the object that should be saved to xml
     */
    @Override
    public void saveObjectToXML(String path, Object objectToBeSaved) {
        String myRawXML = mySerializer.toXML(objectToBeSaved);
        writeToXML(path, myRawXML);
    }

    /**
     * Loads an object at the specified path from xml
     * @param path path to the xml file of the serialized object you wish to deserialize
     * @return a deserialized xml object at path
     * @throws FileNotFoundException when the specified path doesn't point to a valid file
     */
    @Override
    public Object loadObjectFromXML(String path) throws FileNotFoundException{
        String rawXML = readFromXML(path);
        return mySerializer.fromXML(rawXML);
    }

    /**
     * Saves game data to the database in the form of serialized xml of a game object
     * @param gameName name of the game -> folder to be created
     * @param authorName name of the author of the game
     * @param gameObject the object containing all game information except for assets
     */
    @Override
    public void saveGameData(String gameName, String authorName, Object gameObject){
        String myRawXML = mySerializer.toXML(gameObject);
        try {
            myDatabaseEngine.updateGameEntryData(gameName, authorName, myRawXML);
        } catch (SQLException e) {
            System.out.println("Couldn't update game entry data: " + e.getMessage());
        }
    }

    /**
     * Saves game data to the database in the form of serialized xml using a default author name
     * @param gameName name of the game to be saved
     * @param gameObject the game object to be serialized
     */
    @Deprecated
    public void saveGameData(String gameName, Object gameObject) {
        saveGameData(gameName, DEFAULT_AUTHOR, gameObject);
    }

    /**
     * Loads and deserializes all the game info objects from the database to pass to the game center
     * @return deserialized game center data objects
     */
    @Override
    public List<Object> loadAllGameInfoObjects(){
        List<Object> gameInfoObjects = new ArrayList<>();
        List<String> gameInfoObjectXMLs = new ArrayList<>();
        try {
            gameInfoObjectXMLs = myDatabaseEngine.loadAllGameInformationXMLs();
        } catch (SQLException e) {
            System.out.println("Couldn't load game information xmls: " + e.getMessage());
        }
        for (String xml : gameInfoObjectXMLs){
            gameInfoObjects.add(mySerializer.fromXML(xml));
        }
        return gameInfoObjects;
    }

    /**
     * Saves game information (game center data) to the data base
     * @param gameName name of the game
     * @param authorName name of the author of the game
     * @param gameInfoObject the game center data object to be serialized and saved
     */
    @Override
    public void saveGameInfo(String gameName, String authorName, Object gameInfoObject){
        String myRawXML = mySerializer.toXML(gameInfoObject);
        try {
            myDatabaseEngine.updateGameEntryInfo(gameName, authorName, myRawXML);
        } catch (SQLException e) {
            System.out.println("Couldn't update game entry information: " + e.getMessage());
        }
    }

    /**
     * Save the game information (game center data) object using a default author name
     * @param gameName name of the game to be saved
     * @param gameInfoObject the game center data object to be serialized
     */
    @Deprecated
    public void saveGameInfo(String gameName, Object gameInfoObject){
        saveGameInfo(gameName, DEFAULT_AUTHOR, gameInfoObject);
    }

    /**
     * Loads the deserialized game data for gameName, needs to be cast and the cast should be checked
     * @param gameName the game whose data is to be loaded
     * @return deserialized game data that should be cast to a game object and the cast should be checked
     */
    @Override
    public Object loadGameData(String gameName) {
        Object ret = null;
        try {
            ret = mySerializer.fromXML(myDatabaseEngine.loadGameData(gameName));
        } catch (SQLException e) {
            System.out.println("Couldn't load game date from database: " + e.getMessage());
        }
        return ret;
    }

    /**
     * Utility method for saving game data from a local folder of xml files of serialized game data
     * @param gameName name of the game to be saved
     */
    public void saveGameDataFromFolder(String gameName){
        try {
            myDatabaseEngine.updateGameEntryInfo(gameName,
                    DEFAULT_AUTHOR, readFromXML("created_games/"+gameName+"/"+GAME_INFO+XML_EXTENSION));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e){
            System.out.println("Couldn't update game entry info: " + e.getMessage());
        }
    }

    private String readFromXML(String path) throws FileNotFoundException {
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        StringBuilder rawXML = new StringBuilder();
        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                rawXML.append(currentLine);
            }
        } catch (IOException e) {
            System.out.println("Cannot read XML file");
            throw new FileNotFoundException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ex) {
                System.out.println("Could not close files");
            }
        }
        return rawXML.toString();
    }

    /**
     * Saves an image to the database
     * @param imageName the name of the image to save
     * @param imageToSave the image file that should be saved
     */
    @Override
    public void saveImage(String imageName, File imageToSave){
        myDatabaseEngine.saveImage(imageName, imageToSave);
    }

    /**
     * Saves a sound to the database
     * @param soundName name of the sound to be saved
     * @param soundToSave sound file to be saved
     */
    @Override
    public void saveSound(String soundName, File soundToSave){
        myDatabaseEngine.saveSound(soundName, soundToSave);
    }

    /**
     * Loads a sound from the database
     * @param soundName name of the sound to be loaded
     * @return an input stream of sound data to be converted to a media object
     */
    @Override
    public InputStream loadSound(String soundName){
        return myDatabaseEngine.loadSound(soundName);
    }

    /**
     * Loads an image from the database
     * @param imageName name of the image to be loaded
     * @return an input stream of image data to be converted to an image object
     */
    @Override
    public InputStream loadImage(String imageName){
        return myDatabaseEngine.loadImage(imageName);
    }

//    public List<String> getGameNames(){
//        File file = new File(CREATED_GAMES_DIRECTORY);
//        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
//        System.out.println(Arrays.toString(directories));
//        if (directories != null) {
//            return Arrays.asList(directories);
//        }
//        return new ArrayList<>();
//    }

//    private String transformGameNameToPath(String gameName, String filename) {
//        return CREATED_GAMES_DIRECTORY + File.separator + gameName + File.separator + filename + XML_EXTENSION;
//    }

    private void writeToXML(String path, String rawXML){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(path));
            fileWriter.write(rawXML);
        } catch (IOException exception){
            System.out.println("In catch block");
            exception.printStackTrace();
        } finally {
            System.out.println("In finally block");
            try {
                if (fileWriter != null){
                    fileWriter.close();
                }
            } catch (IOException e){
                System.out.println("Couldn't close file");
            }
        }
    }

    /**
     * Creates a user in the data base
     * @param userName name of the user
     * @param password user's password
     * @return true if the user was successfully created
     */
    @Override
    public boolean createUser (String userName, String password){
        boolean success = false;
        try {
            success =myDatabaseEngine.createUser(userName, password);
        } catch (SQLException e) {
            System.out.println("Could not create user: " + e.getMessage());
        }
        return success;
    }

    /**
     * Validates a user's login attempt
     * @param userName entered user name
     * @param password entered password
     * @return true if valid user name and password combination
     */
    @Override
    public boolean validateUser (String userName, String password){
        return myDatabaseEngine.authenticateUser(userName, password);
    }
}
