package runner.external;

import engine.external.Entity;
import engine.external.PositionComponent;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

    public GameRunner(List<Entity> entities, int width, int height, Stage stage){
        myEntities = entities;
        mySceneWidth = width;
        mySceneHeight = height;
        myStage = stage;
        myGroup = new Group();
        myScene = new Scene(myGroup);
        myCanvas = new Canvas(mySceneWidth, mySceneHeight);
        myGroup.getChildren().add(myCanvas);
        myGraphicsContext = myCanvas.getGraphicsContext2D();
        myScene.setFill(Color.BEIGE);
        showEntities(myGraphicsContext);
        myStage.setScene(myScene);
    }



    public void display() {
        myStage.show();
    }

    private void showEntities(GraphicsContext graphics){
        for(Entity entity : myEntities){
            PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);

            System.out.println(positionComponent);
            Point3D position = (Point3D) positionComponent.getValue();
            Double xPosition = position.getX();
            Double yPosition = position.getY();

            graphics.drawImage(new Image("basketball.png", 50, 50, true, true), xPosition, yPosition);
        }
    }
}
