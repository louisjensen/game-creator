package runner.external;

import engine.external.Entity;
import engine.external.PositionComponent;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import runner.internal.TestEngine;

import java.util.List;

public class GameRunner {
    /**
     * This will be the primary class that creates a new game engine
     * and displays sprites on a stage
     */
    private List<Entity> myEntities;
    private int mySceneWidth;
    private int mySceneHeight;
    private Stage myStage;
    private Group myGroup;
    private Scene myScene;
    private Canvas myCanvas;
    private GraphicsContext myGraphicsContext;
    private TestEngine myTestEngine;
    private Timeline myAnimation;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;


    public GameRunner(List<Entity> entities, int width, int height, Stage stage) {
        myEntities = entities;
        mySceneWidth = width;
        mySceneHeight = height;
        myStage = stage;
        myGroup = new Group();
        myScene = new Scene(myGroup, mySceneWidth, mySceneHeight);
       // myCanvas = new Canvas(mySceneWidth, mySceneHeight);
       // myGroup.getChildren().add(myCanvas);
       // myGraphicsContext = myCanvas.getGraphicsContext2D();
        myScene.setFill(Color.BEIGE);
        showEntities();
        myStage.setScene(myScene);
        myTestEngine = new TestEngine(myEntities);

        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();

        myStage.show();
    }

    int x = 0;
    private void step (double elapsedTime) {
        System.out.println(x); x++;
        myTestEngine.update();
//        myGraphicsContext.restore();
        showEntities();
    }



    private void showEntities(){
        myGroup.getChildren().clear();
        for(Entity entity : myEntities){
            PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
            Point3D position = (Point3D) positionComponent.getValue();
//            ImageView image = new ImageView("basketball.png");
//            image.setFitWidth(50);
//            image.setPreserveRatio(true);
//            image.setSmooth(false);
//            image.setLayoutX(position.getX());
//            image.setLayoutY(position.getY());
            Circle circle = new Circle(position.getX(), position.getY(), 25);
            myGroup.getChildren().add(circle);
           // graphics.drawImage(new Image("basketball.png", 50, 50, true, true), position.getX(), position.getY());

        }
    }


}
