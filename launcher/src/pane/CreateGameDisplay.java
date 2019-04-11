package pane;

import controls.InformativeField;
import data.external.DataManager;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manager.SwitchToAuthoring;
import manager.SwitchToUserOptions;
import runner.external.GameCenterData;

import java.io.File;

public class CreateGameDisplay extends VBox {
    private static final String FOLDER_KEY = "user_files";
    private static final String CREATE_LAUNCHER = "create";
    private static final String GAME_FIELD = "Enter Game Name";
    private static final String GAME_DESCRIPITON = "Enter Game Description";
    private File myFile;
    private InformativeField gameName = new InformativeField(GAME_FIELD);
    private InformativeField gameDescription = new InformativeField(GAME_DESCRIPITON);
    public CreateGameDisplay(SwitchToAuthoring sceneSwitch){
        this.getStyleClass().add("default_launcher.css");
        setUpImages(sceneSwitch);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);

    }
    private void setUpImages(SwitchToAuthoring sceneSwitch){
        LauncherControlDisplay myCreator = new LauncherControlDisplay(FOLDER_KEY);
        myCreator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                 myFile =  fileChooser.showOpenDialog(new Stage());
                }
         });
        LauncherControlDisplay myPlayer = new LauncherControlDisplay(CREATE_LAUNCHER);
        myPlayer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DataManager dm = new DataManager();
                dm.createGameFolder("GameOne");
                GameCenterData myDataCenter = new GameCenterData(gameName.getTextEntered(), gameDescription.getTextEntered(), "GameOne", myFile.toURI().toString());
                sceneSwitch.switchScene(myDataCenter);
            }
        });

        makeGameInfo(gameName,200);
        makeGameInfo(gameDescription, 300);
        this.getChildren().add(myCreator);
        this.getChildren().add(gameName);
        this.getChildren().add(gameDescription);
        this.getChildren().add(myPlayer);

    }

    private void makeGameInfo(InformativeField field, int buttonWidth){
        field.setMaxWidth(buttonWidth);
        field.setAlignment(Pos.CENTER);

    }

}
