package runner.internal;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.Level;
import engine.external.component.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;
import java.util.function.Consumer;

public class LevelRunner {
    private PauseButton myPauseButton;
    private Node myPause;
    private Collection<Entity> myEntities;
    private int mySceneWidth;
    private int mySceneHeight;
    private Stage myStage;
    private Group myGroup;
    private Scene myScene;
    private Engine myEngine;
    private Timeline myAnimation;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Level myLevel;
    private Set<KeyCode> myCurrentKeys;
    private boolean canPause = false;
    private Consumer<Double> myLevelChanger;
    private ProgressionSystem myProgressionSystem;
    private ScrollingSystem myScrollingSystem;
    private ImageDisplaySystem myImageDisplaySystem;
    private ScoringSystem myScoringSystem;
    private LivesSystem myLivesSystem;
    private LevelSystem myLevelSystem;

    public LevelRunner(Level level, int width, int height, Stage stage, Consumer playNext){
        myLevel = level;
        mySceneWidth = width;
        mySceneHeight = height;
        myCurrentKeys = new HashSet<>();
        myEngine = new Engine(level);
        myEntities = myEngine.updateState(myCurrentKeys);
        myLevelChanger = playNext;
        myAnimation = new Timeline();
        buildStage(stage);
        startAnimation();
        addPauseButton();
        myStage.show();
    }

    private void initializeSystems() {
        Collection<Class<? extends Component>> systemComponents = new ArrayList<>();
        systemComponents.add(ProgressionComponent.class);
        systemComponents.add(NextLevelComponent.class);
        myProgressionSystem = new ProgressionSystem(systemComponents, this,
                myGroup, myStage, myAnimation, mySceneWidth, mySceneHeight, myLevelChanger);
        systemComponents.clear();
        systemComponents.add(CameraComponent.class);
        myScrollingSystem = new ScrollingSystem(systemComponents, this, myGroup, myScene);

        systemComponents.clear();
        systemComponents.add(ImageViewComponent.class);
        myImageDisplaySystem = new ImageDisplaySystem(systemComponents, this, myGroup);

        Collection<Class<? extends Component>> comps = new ArrayList<>();
        comps.add(ScoreComponent.class);
        myScoringSystem = new ScoringSystem(comps, this);

        Collection<Class<? extends Component>> comp1 = new ArrayList<>();
        comp1.add(ScoreComponent.class);
        myLivesSystem = new LivesSystem(comp1, this);

        Collection<Class<? extends Component>> comp2 = new ArrayList<>();
        comp2.add(NextLevelComponent.class);
        myLevelSystem = new LevelSystem(comp2, this);
    }

    private void addPauseButton() {
        myPauseButton = new PauseButton(myAnimation);
        myPause = myPauseButton.getPauseButton();
        myGroup.getChildren().add(myPause);
        canPause = true;
    }

    private void buildStage(Stage stage) {
        myStage = stage;
        myGroup = new Group();
        myScene = new Scene(myGroup, mySceneWidth, mySceneHeight);
        myScene.setFill(Color.BEIGE);
        myScene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        myScene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
        initializeSystems();
        showEntities();
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
        showEntities();
    }

    private void showEntities(){
        myGroup.getChildren().retainAll(myPause);
        myProgressionSystem.update(myEntities);
        myScrollingSystem.update(myEntities);
        myImageDisplaySystem.update(myEntities);
        myScoringSystem.update(myEntities);
        myLivesSystem.update(myEntities);
        myLevelSystem.update(myEntities);
        if (canPause) movePauseButton();
    }

    private void movePauseButton(){
        myPause.setLayoutX(myPauseButton.getButtonX() - myGroup.getTranslateX());
    }

    public Collection<Entity> getEntities(){
        return myEntities;
    }
}

