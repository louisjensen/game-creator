package manager;

import javafx.scene.Scene;
import javafx.stage.Stage;
import page.CreateNewGamePage;
import page.SplashPage;
import page.WelcomeUserPage;
import runner.external.Game;
import runner.external.GameCenterData;
import ui.main.MainGUI;

public class SceneManager {
    private static final String MY_STYLE = "default.css";
    private static final double STAGE_WIDTH = 700;
    private static final double STAGE_HEIGHT = 600;
    private Scene myScene;
    private SplashPage myInitialPage;
    private WelcomeUserPage myWelcomeUserPage;
    private CreateNewGamePage myNewGamePage;

    private SwitchToUserOptions switchToWelcomeUserPage = new SwitchToUserOptions() {
        @Override
        public void switchPage() {
            goToWelcomeUserPage();
        }
    };
    private SwitchToUserOptions switchToNewGamePage = new SwitchToUserOptions() {
        @Override
        public void switchPage() {
            goToUserOptions();
        }
    };
    private SwitchToAuthoring switchToAuthoring = new SwitchToAuthoring() {
        @Override
        public void switchScene(GameCenterData myCenterData) {
            goToAuthoring(myCenterData);
        }
    };
    private SwitchToUserOptions switchToGameCenter = new SwitchToUserOptions(){
        @Override
        public void switchPage() {
            goToGameCenter();
        }
    };

    public void render(Stage myStage){
        makePages();
        myScene = new Scene(myInitialPage,STAGE_WIDTH,STAGE_HEIGHT);
        myScene.getStylesheets().add(MY_STYLE);
        myStage.setScene(myScene);
        myStage.show();
    }
    private void makePages(){
        myWelcomeUserPage = new WelcomeUserPage(switchToNewGamePage);
        myInitialPage = new SplashPage(switchToWelcomeUserPage);
        myNewGamePage = new CreateNewGamePage(switchToAuthoring);
    }

    private void goToWelcomeUserPage(){
        myScene.setRoot(myWelcomeUserPage);
    }
    private void goToUserOptions(){
        myScene.setRoot(myNewGamePage);
    }
    private void goToAuthoring(GameCenterData myCenter){
        MainGUI myGUI = new MainGUI(new Game(),myCenter);
        myGUI.launch();
    }

    private void goToGameCenter(){

    }
}
