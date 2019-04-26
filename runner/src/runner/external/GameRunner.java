package runner.external;

import data.external.DataManager;
import engine.external.Level;
import javafx.stage.Stage;
import runner.internal.DummyGameObjectMaker;
import runner.internal.LevelRunner;
import java.io.FileNotFoundException;
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

    public GameRunner(String gameName, String authorName) throws FileNotFoundException {
        myGame = loadGameObject(gameName, authorName);
        myGameStage = new Stage();
        Level levelOne = myGame.getLevels().get(0);
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
        Level currentLevel = myGame.getLevels().get(currentLevelNumber - 1);
        mySceneWidth = myGame.getWidth();
        mySceneHeight = myGame.getHeight();
        Consumer<Double> goToNext = (level) -> {
            nextLevel(level);
        };
        new LevelRunner(currentLevel, mySceneWidth, mySceneHeight, myGameStage, goToNext);
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