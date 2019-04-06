package ui.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.ErrorBox;
import ui.TestEntity;
import ui.UIException;
import ui.manager.AssetManager;
import ui.manager.LabelManager;
import ui.manager.ObjectManager;

public class AssetManagerTester extends Application {

    @Override
    public void start(Stage testStage) {
        LabelManager labelmanager = new LabelManager();
        ObjectManager manager = new ObjectManager(labelmanager);
        AssetManager temp = new AssetManager(new TestEntity("object1", manager));
        String selected = temp.showAndReturn("flappy_bird.png");
        System.out.println("Selected Image: " + selected);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
