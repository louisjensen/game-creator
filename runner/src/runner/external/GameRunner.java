package runner.external;

import data.external.DataManager;
import engine.external.Level;
import javafx.stage.Stage;
import runner.internal.DummyGameObjectMaker;
import runner.internal.LevelRunner;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class GameRunner {
    /**
     * This will be the primary class that creates a new game engine
     * and displays sprites on a stage
     */
    private int mySceneWidth;
    private int mySceneHeight;
    private List<Level> myLevels;
    private Game myGame;
    private Stage myGameStage;
    private String myGameName;
    private String myAuthorName;

    public GameRunner(String gameName, String authorName) throws FileNotFoundException {
        myGame = loadGameObject(gameName, authorName);
        myGameName = gameName;
        myAuthorName = authorName;
        myGameStage = new Stage();
        int firstLevel = 1;
        runLevel(firstLevel);
    }

    private Game loadGameObject(String gameName, String authorName){
        DummyGameObjectMaker dm2 = new DummyGameObjectMaker();
        Game gameMade = dm2.getGame(gameName);
        DataManager dm = new DataManager();
        dm.saveGameData(gameName, authorName,gameMade);
        System.out.println("Serialization complete");
        try {
            return (Game) dm.loadGameData(gameName, authorName);
        } catch (SQLException e) {
            return null;
        }
//        return gameMade;
    }


    private void runLevel(int currentLevelNumber){
        DataManager dm = new DataManager();
        dm.saveGameData(myGameName, myAuthorName, myGame);
        Game gameToPlay;
        try {
            gameToPlay = (Game) dm.loadGameData(myGameName, myAuthorName);
        } catch(SQLException e){
            gameToPlay = myGame;
        }
        Level currentLevel = gameToPlay.getLevels().get(currentLevelNumber - 1);
        mySceneWidth = myGame.getWidth();
        mySceneHeight = myGame.getHeight();
        Consumer<Double> goToNext = (level) -> {
            nextLevel(level);
        };
        new LevelRunner(currentLevel, mySceneWidth, mySceneHeight, myGameStage,
                goToNext, gameToPlay.getLevels().size());
    }

    private void nextLevel(Double level) {
        int levelToPlay;
        try{
            levelToPlay = level.intValue();
        } catch (Exception e){
            levelToPlay = 10;
        }
        System.out.println("progressing to level " + levelToPlay);
        this.runLevel(levelToPlay);
    }

}