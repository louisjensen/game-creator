package ui.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.ErrorBox;
import ui.TestEntity;
import ui.TestLevel;
import ui.UIException;
import ui.manager.LabelManager;
import ui.manager.ObjectManager;

/**
 * @author Harry Ross
 */
public class PaneTester extends Application {

    private static final String TEST_STYLESHEET = "default.css";

    @Override
    public void start(Stage testStage) {
        testStage.setTitle("Pane Test");
        testStage.setResizable(false);
        LabelManager testLabels = new LabelManager();
        addTestLabels(testLabels);
        ObjectManager manager = new ObjectManager(testLabels);
        TestEntity obj1 = populateTestObjects(manager);
        try {
            PropertiesPane testPaneObj =
                    new PropertiesPane("Object", obj1, "object_properties_list", testLabels);
            PropertiesPane testPaneLvl =
                    new PropertiesPane("Level", new TestLevel("Level_1", manager), "level_properties_list", testLabels);
            PropertiesPane testPaneIns =
                    new PropertiesPane("Instance", obj1, "instance_properties_list", testLabels);

            Scene testScene = new Scene(testPaneLvl);

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
        testLabels.addLabel("Label", "Mario");
        testLabels.addLabel("Label", "Goomba");
        testLabels.addLabel("Label", "Brick_Block");
    }

    private TestEntity populateTestObjects(ObjectManager manager) {
        TestEntity a = new TestEntity("object1", manager);
        TestEntity b = new TestEntity("object2", manager);
        TestEntity c = new TestEntity("object3", manager);
        TestEntity d = new TestEntity("object4", manager);
        TestEntity e = new TestEntity("object1", manager);
        TestEntity f = new TestEntity("object1", manager);
        manager.addEntity(a);
        manager.addEntity(b);
        manager.addEntity(c);
        manager.addEntity(d);
        manager.addEntity(e);
        manager.addEntity(f);
        return f;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
