package launcher;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import page.WelcomePage;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }
    public void start (Stage myStage) throws Exception {
        WelcomePage myPage = new WelcomePage();
        myPage.render(myStage);
    }

}
