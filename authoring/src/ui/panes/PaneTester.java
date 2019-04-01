package ui.panes;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class PaneTester extends Application {

    private static final String TEST_STYLESHEET = "default.css";

    @Override
    public void start(Stage testStage) {
        testStage.setTitle("Pane Test");
        String[] test = {"Item1", "Item2", "Item3", "Item4", "Item5"};
        ArrayList<String> testList = new ArrayList<>(Arrays.asList(test));
        PropertiesPane testPane = new PropertiesPane("Test", testList); // Change to test a different pane type
        Scene testScene = new Scene(testPane);
        testScene.getStylesheets().add(TEST_STYLESHEET);
        testStage.setScene(testScene);
        testStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
