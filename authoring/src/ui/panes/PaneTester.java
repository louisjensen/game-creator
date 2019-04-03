package ui.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.ErrorBox;
import ui.TestEntity;
import ui.TestLevel;
import ui.UIException;
import ui.manager.LabelManager;

public class PaneTester extends Application {

    private static final String TEST_STYLESHEET = "default.css";

    @Override
    public void start(Stage testStage) {
        testStage.setTitle("Pane Test");
        LabelManager testLabels = new LabelManager();
        addTestLabels(testLabels);
        try {
            PropertiesPane testPaneObj =
                    new PropertiesPane("Object", new TestEntity(), "object_properties_list", testLabels);
            PropertiesPane testPaneLvl =
                    new PropertiesPane("Level", new TestLevel(), "level_properties_list", testLabels);

            Scene testScene = new Scene(testPaneObj);

            testScene.getStylesheets().add(TEST_STYLESHEET);
            testStage.setScene(testScene);
        } catch (UIException e) {
            ErrorBox errorbox = new ErrorBox("Test", e.getMessage());
            errorbox.display();
        }
        testStage.show();
    }

    private void addTestLabels(LabelManager testLabels) {
        testLabels.addLabel("Group", "Enemies");
        testLabels.addLabel("Group", "Platforms");
        testLabels.addLabel("Object", "Mario");
        testLabels.addLabel("Object", "Goomba");
        testLabels.addLabel("Object", "Brick_Block");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
