package manager;

import controls.SceneSwitchButton;
import javafx.scene.Scene;
import javafx.stage.Stage;
import page.WelcomePage;
import page.WelcomeUserPage;

public class SceneManager {
    private static final String MY_STYLE = "default.css";
    private static final double STAGE_WIDTH = 700;
    private static final double STAGE_HEIGHT = 600;
    private Scene myScene;
    private WelcomeUserPage myWelcomeUserPage;
    private SwitchToUserOptions switchToWelcomeUserPage = new SwitchToUserOptions() {
        @Override
        public void switchPage() {
            goToWelcomeUserPage();
        }
    };
    public void render(Stage myStage){
        WelcomePage myWelcomePage = new WelcomePage(switchToWelcomeUserPage);
        makePages();
        myScene = new Scene(myWelcomePage,STAGE_WIDTH,STAGE_HEIGHT);
        myScene.getStylesheets().add(MY_STYLE);
        myStage.setScene(myScene);
        myStage.show();
    }
    private void makePages(){
        myWelcomeUserPage = new WelcomeUserPage();
    }

    public void goToWelcomeUserPage(){
        myScene.setRoot(myWelcomeUserPage);
    }
}
