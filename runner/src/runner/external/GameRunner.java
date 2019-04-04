package runner.external;

import engine.external.Component;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameRunner {
    /**
     * This will be the primary class that creates a new game engine
     * and displays sprites on a stage
     */
    private List<Component> myComponents;
    private int mySceneWidth;
    private int mySceneHeight;
    private Stage myStage;
    private Group myGroup;
    private Scene myScene;

    public GameRunner(List<Component> components, int width, int height, Stage stage){
       // myComponents = components;
        mySceneWidth = width;
        mySceneHeight = height;
        myStage = stage;
        myGroup = new Group();
        myScene = new Scene(myGroup, mySceneWidth, mySceneHeight, Color.GREEN);
        myStage.setScene(myScene);
    }

    public void display() {
        myStage.show();
    }
}

//    public GUIDisplay(Stage stage){
//        myLanguage = DEFAULT_LANGUAGE;
//        myStage = stage;
//        myListOfCommands = new ArrayList<>();
//        dataTracker = new GUIdata();
//        myRoot = createGridPane();
//        myDelegator = new Delegator(myStackedCanvasPane, myTabExplorer, myPaletteTabExplorer, myToolbar);
//        myRoot.setGridLinesVisible(false);
//        myScene = new Scene(myRoot, SCENE_WIDTH, SCENE_HEIGHT, Color.LIGHTGRAY);
//        myStage.setScene(myScene);
//    }
