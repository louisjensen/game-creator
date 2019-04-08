package runner.external;

import engine.external.Level;

import java.util.ArrayList;
import java.util.Collection;

public class Game {
    private Collection<Level> myLevels;
    private String myIconName;
    private String myGameName;
    private String myGameDescription;
    private int myWidth;
    private int myHeight;

    public Game(){
        myLevels = new ArrayList<Level>();
    }

    public void addLevel(Level level){
        myLevels.add(level);
    }

    public Collection<Level> getLevels(){
        return myLevels;
    }

    public int getWidth(){
        return myWidth;
    }

    public int getHeight(){
        return myHeight;
    }
}
