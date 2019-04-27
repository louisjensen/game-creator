package runner.internal;

import javafx.scene.control.Button;
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

    public PauseScreen(Consumer toggle, Stage stage, Double translatedX){
        myPauseMenu = new VBox(8); // spacing = 8
        initializeButtons();
        myPauseMenu.getChildren().addAll(myResumeButton, myRestartButton, myExitButton);
        System.out.println(200-translatedX);
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

        });
        myExitButton = new Button("Exit Game");
        myExitButton.setOnMouseClicked(event ->{
            myStage.close();
        });
    }

    public VBox getPauseMenu(){
        return myPauseMenu;
    }
}
