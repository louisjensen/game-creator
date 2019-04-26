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
import javafx.scene.image.ImageView;
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

    public LevelRunner(Level level, int width, int height, Stage stage, Consumer playNext){
        myLevel = level;
        mySceneWidth = width;
        mySceneHeight = height;
        myCurrentKeys = new HashSet<>();
        myEngine = new Engine(level);
        myEntities = myEngine.updateState(myCurrentKeys);
        myLevelChanger = playNext;
        buildStage(stage);
        //initializeSystems();
        startAnimation();
        addPauseButton();
        myStage.show();
    }

    private void initializeSystems() {
        Collection<Class<? extends Component>> systemComponents = new ArrayList<>();
        systemComponents.add(ProgressionComponent.class);
        systemComponents.add(NextLevelComponent.class);
        myProgressionSystem = new ProgressionSystem(systemComponents, this);

        systemComponents.clear();
        systemComponents.add(CameraComponent.class);
        myScrollingSystem = new ScrollingSystem(systemComponents, this, myGroup, myScene);
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
        myAnimation = new Timeline();
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
        for(Entity entity : myEntities){
            try {
                System.out.println(entity.getComponent(NextLevelComponent.class).getValue());
                System.out.println(entity.getComponent(ProgressionComponent.class).getValue());
            } catch (Exception e){
                //do nothing
            }
            if (entity.hasComponents(ScoreComponent.class)) {
                System.out.println("Score: " + entity.getComponent(ScoreComponent.class).getValue());
            }
            ImageViewComponent imageViewComponent = (ImageViewComponent) entity.getComponent(ImageViewComponent.class);
            try {
                ImageView image = imageViewComponent.getValue();
                myGroup.getChildren().add(image);
            }
            catch (NullPointerException e) {
                 //TODO fix this
            }
        }
        if (canPause) {
            movePauseButton();
        }
    }

    public void endLevel(Double levelToProgressTo) {
        myGroup.getChildren().clear();
        myAnimation.stop();
        myStage.setScene(new Scene(new Group(), mySceneWidth, mySceneHeight));
        myLevelChanger.accept(levelToProgressTo);
    }

    private void movePauseButton(){
        myPause.setLayoutX(myPauseButton.getButtonX() - myGroup.getTranslateX());
    }

    public Collection<Entity> getEntities(){
        return myEntities;
    }
}

