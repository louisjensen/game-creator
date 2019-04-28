package runner.external;

import engine.external.Entity;
import engine.external.Level;
import java.util.*;

/**
 * Game Object contains all information to play a game
 * @author Louis Jensen
 */
public class Game {
    private List<Level> myLevels;
    private Map<String, Entity> myUserCreatedTypes;
    private String myIconName;
    private String myGameName;
    private String myGameDescription;
    private int myWidth;
    private int myHeight;

    /**
     * Constructor to create a game object with initial size
     */
    public Game(){
        myLevels = new ArrayList<Level>();

        //Temporary defaults
        myHeight = 500;
        myWidth = 500;
    }

    /**
     * Adds a level to the game object
     * @param level - built level to add
     */
    public void addLevel(Level level){
        myLevels.add(level);
    }

    /**
     * Gets list of levels
     * @return Unmodifiable list of levels
     */
    public List<Level> getLevels(){
        return Collections.unmodifiableList(myLevels);
    }

    /**
     * Gets scene width
     * @return int scene width
     */
    public int getWidth(){
        return myWidth;
    }

    /**
     * Gets scene height
     * @return int scene height
     */
    public int getHeight(){
        return myHeight;
    }

    /**
     * Adds types created by user
     * @param userCreatedTypes - map of user defined type
     */
    public void addUserCreatedTypes(Map<String, Entity> userCreatedTypes){
        myUserCreatedTypes = userCreatedTypes;
    }
}
