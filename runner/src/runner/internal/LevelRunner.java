package runner.internal;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.Level;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import runner.internal.runnerSystems.*;
import java.util.*;
import java.util.function.Consumer;

public class LevelRunner {
    private PauseButton myPauseButton;
    private Node myPause;
    private Collection<Entity> myEntities;
    protected int mySceneWidth;
    protected int mySceneHeight;
    protected Stage myStage;
    protected Group myGroup;
    protected Scene myScene;
    private Engine myEngine;
    protected Timeline myAnimation;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Level myLevel;
    private Set<KeyCode> myCurrentKeys;
    private boolean canPause = false;
    protected Consumer<Double> myLevelChanger;
    private List<RunnerSystem> mySystems;
    protected HeadsUpDisplay myHUD;
    private Text myLabel;

    public LevelRunner(Level level, int width, int height, Stage stage, Consumer playNext){
        myLevel = level;
        mySceneWidth = width;
        mySceneHeight = height;
        myCurrentKeys = new HashSet<>();
        myEngine = new Engine(level);
        myHUD = new HeadsUpDisplay(width);
        myEntities = myEngine.updateState(myCurrentKeys);
        myLevelChanger = playNext;
        myAnimation = new Timeline();
        buildStage(stage);
        startAnimation();
        addButtonsAndHUD();
        myStage.show();
    }

    private void initializeSystems() {
        SystemManager systems = new SystemManager(this);
        mySystems = systems.getSystems();
    }

    private void addButtonsAndHUD() {
        myPauseButton = new PauseButton(myAnimation);
        myPause = myPauseButton.getPauseButton();
        myGroup.getChildren().add(myPause);
        canPause = true;
        myLabel = myHUD.getLabel();
        myGroup.getChildren().add(myLabel);
    }

    private void buildStage(Stage stage) {
        myStage = stage;
        myStage.setResizable(false);
        myGroup = new Group();
        myScene = new Scene(myGroup, mySceneWidth, mySceneHeight);
        myScene.setFill(Color.BEIGE);
        myScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        myScene.getStylesheets().add("runnerStyle.css");
        myScene.getStylesheets().add("https://fonts.googleapis.com/css?family=Gugi");
        initializeSystems();
        updateGUI();
        myStage.setScene(myScene);
    }

    private void startAnimation(){
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    private void handleKeyPress(KeyCode code) {
        myCurrentKeys.add(code);
    }

    private void handleKeyRelease(KeyCode code){
        myCurrentKeys.remove(code);
    }

    private void step (double elapsedTime) {
        myEntities = myEngine.updateState(myCurrentKeys);
        updateGUI();
    }

    private void updateGUI(){
        myGroup.getChildren().retainAll(myPause, myLabel);
        for(RunnerSystem system : mySystems){
            system.update(myEntities);
        }
        if (canPause) updateButtonsAndHUD();
    }

    private void updateButtonsAndHUD(){
        myPause.setLayoutX(myPauseButton.getButtonX() - myGroup.getTranslateX());
        myLabel.setLayoutX(myHUD.getX() - myGroup.getTranslateX());
        myHUD.updateLabel();
    }

    public Collection<Entity> getEntities(){
        return myEntities;
    }
}