package runner.internal;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This creates the screen that appears when the level is completed
 * Boolean value input into constructor tells screen whether to display
 * won or lost
 * @author Louis Jensen
 */
public class GameBeatenScreen {
    private Stage myStage;
    private VBox myNode;

    /**
     * Constructor for the game over screen
     * @param stage - Stage to put screen
     * @param translatedX - Main group's translateX value to ensure screen is placed correctly
     * @param win - Boolean win or lose the game
     */
    public GameBeatenScreen(Stage stage, Double translatedX, Boolean win) {
        myStage = stage;
        Button exit = new Button("Exit Game");
        exit.setOnMouseClicked(event ->{
            myStage.close();
        });
        exit.setId("button");
        String text;
        if (win){
            text = "Congratulations! \n you have completed \n the game";
        } else {
            text = "You have lost the game \n good luck next time!";
        }
        Label congrats = new Label(text);
        congrats.setId("text");
        myNode = new VBox(8);
        myNode.setId("PauseMenu");
        myNode.setLayoutX(180 - translatedX);
        myNode.setLayoutY(180);
        myNode.getChildren().addAll(congrats, exit);
    }

    /**
     * Gets the VBox of the screen
     * @return VBox to display on stage
     */
    public VBox getNode(){
        return myNode;
    }

}
