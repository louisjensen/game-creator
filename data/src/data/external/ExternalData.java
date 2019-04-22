package data.external;

import java.io.FileNotFoundException;
import java.util.List;

public interface ExternalData {

    /*
     * This method will create a folder when a user starts building a new game, and populate it with the xml files, along
     * with their basic outlines, that it will absolutely need for a game. This folder is where authoring will store information
     * from the user's design inputs so that it can be loaded across any platforms that would need access to data
     */
    void createGameFolder(String folderName);

    /**
     * Saves a an object to xml at the path specified by path
     * @param path to the file to be saved
     * @param objectToBeSaved the object that should be saved to xml
     */
    void saveObjectToXML(String path, Object objectToBeSaved);

    /**
     * Load a deserialized version of the object represented by the xml file specified by path
     * @param path path to the xml file of the serialized object you wish to deserialize
     * @return deserialized version of the object that should be cast
     */
    Object loadObjectFromXML(String path) throws FileNotFoundException;

    /**
     * Save a Game of name gameName to the path created_games/gameName/game_data.xml
     * @param gameName name of the game -> folder to be created
     * @param authorName name of the author of the game
     * @param gameObject the object containing all game information except for assets
     */
    void saveGameData(String gameName, String authorName, Object gameObject);

    /**
     * Deserializes the xml file stored at created_games/gameName/game_data.xml into an object
     * @param gameName the game whose data is to be loaded
     * @return the deserialized game data that should then be cast to a game object
     */
    Object loadGameData(String gameName) throws FileNotFoundException;

}
