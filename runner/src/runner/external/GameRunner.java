package runner.external;

import data.external.DataManager;
import engine.external.Level;
import javafx.stage.Stage;
import runner.internal.DummyGameObjectMaker;
import runner.internal.LevelRunner;
import java.io.FileNotFoundException;
import java.util.*;

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

    public GameRunner(String game) throws FileNotFoundException {
        myGame = loadGameObject(game);
        Level levelOne = myGame.getLevels().get(0);
        runLevel(levelOne);
    }

    private Game loadGameObject(String path){
        DummyGameObjectMaker dm2 = new DummyGameObjectMaker();
        Game gameMade = dm2.getGame(path);
        DataManager dm = new DataManager();
        dm.createGameFolder("YeetRevised2");
        dm.saveGameData("YeetRevised2", gameMade);
        System.out.println("Serialization complete");
        return (Game) dm.loadGameData("YeetRevised2");
    }

    private void runLevel(Level currentLevel){
        mySceneWidth = myGame.getWidth();
        mySceneHeight = myGame.getHeight();
        myGameStage = new Stage();
        new LevelRunner(currentLevel, mySceneWidth, mySceneHeight, myGameStage);
    }
}