package page;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pane.UserStartDisplay;
import pane.WelcomeDisplay;

public class WelcomePage extends VBox{
    private static final String MY_STYLE = "default.css";
    private static final double STAGE_WIDTH = 700;
    private static final double STAGE_HEIGHT = 600;

    private HBox myWelcomeDisplay = new WelcomeDisplay();
    private HBox myUserAccountDisplay = new UserStartDisplay();

    public void render(Stage myStage){
        Scene myScene = new Scene(this,STAGE_WIDTH,STAGE_HEIGHT);
        myScene.getStylesheets().add(MY_STYLE);
        createPanes();
        myStage.setScene(myScene);
        myStage.show();
    }
    private void createPanes(){
        this.getStyleClass().add(MY_STYLE);
        this.getChildren().add(0,myWelcomeDisplay);
        this.getChildren().add(1,myUserAccountDisplay);


    }

}
