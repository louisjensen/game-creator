package launcher;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import manager.SceneManager;
import page.WelcomePage;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }
    public void start (Stage myStage) throws Exception {
        SceneManager myScene = new SceneManager();
        myScene.render(myStage);
    }

}
