package ui.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AssetManagerTester extends Application {

    @Override
    public void start(Stage testStage) {
        //AssetManager temp = new AssetManager();
        //temp.show();
        UserCreatedTypesPane userCreatedTypesPane = new UserCreatedTypesPane();
        DefaultTypesPane entityTypePane = new DefaultTypesPane(userCreatedTypesPane);
        Viewer viewer = new Viewer(1000, 1000);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(viewer, entityTypePane, userCreatedTypesPane);
        entityTypePane.setPrefSize(300, 600);
        Scene scene = new Scene(hBox, 600, 600);
        //scene.getStylesheets().add("default.css");
        testStage.setScene(scene);
        testStage.show();
        //Stage viewerStage = new Stage();
        //Scene viewerScene = new Scene(new Viewer(1000, 1000), 500, 500);
        //viewerStage.setScene(viewerScene);
        //viewerStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
