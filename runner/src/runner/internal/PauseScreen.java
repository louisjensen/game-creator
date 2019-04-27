package runner.internal;

import engine.external.Entity;
import engine.external.component.ProgressionComponent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class PauseScreen {
    private VBox myPauseMenu;
    private Button myResumeButton;
    private Button myRestartButton;
    private Button myExitButton;
    private Consumer myToggler;
    private Stage myStage;
    private LevelRunner myLevelRunner;

    public PauseScreen(LevelRunner levelRunner, Consumer toggle, Stage stage, Double translatedX){
        myPauseMenu = new VBox(8); // spacing = 8
        myLevelRunner = levelRunner;
        initializeButtons();
        myPauseMenu.getChildren().addAll(myResumeButton, myRestartButton, myExitButton);
        myPauseMenu.setLayoutX(200 - translatedX);
        myPauseMenu.setLayoutY(200);
        myToggler = toggle;
        myStage = stage;
    }

    private void initializeButtons() {
        myResumeButton = new Button("Resume");
        myResumeButton.setOnMouseClicked(event ->{
            myToggler.accept(null);
        });
        myRestartButton = new Button("Restart Level");
        myRestartButton.setOnMouseClicked(event ->{
            restartLevel();
        });
        myExitButton = new Button("Exit Game");
        myExitButton.setOnMouseClicked(event ->{
            myStage.close();
        });
    }

    private void restartLevel() {
        for(Entity entity : myLevelRunner.getEntities()){
            if(entity.hasComponents(ProgressionComponent.class)){
                ((ProgressionComponent)entity.getComponent(ProgressionComponent.class)).setValue(true);
                break;
            }
        }
        myToggler.accept(null);
    }

    public VBox getPauseMenu(){
        return myPauseMenu;
    }
}
