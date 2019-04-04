package ui.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.ErrorBox;
import ui.TestEntity;
import ui.UIException;
import ui.manager.AssetManager;

public class AssetManagerTester extends Application {

    @Override
    public void start(Stage testStage) {
        AssetManager temp = new AssetManager(new TestEntity());
        String selected = temp.showAndReturn("flappy_bird.png");
        NewEntityTypePane entityTypePane = new NewEntityTypePane();
        Scene scene = new Scene(entityTypePane, 600, 600);
        testStage.setScene(scene);
        testStage.show();
        System.out.println("Selected Image: " + selected);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
