package data.external;

import java.io.FileNotFoundException;
import java.util.List;

public interface ExternalData {

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
