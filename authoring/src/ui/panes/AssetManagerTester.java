package ui.panes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ui.ErrorBox;
import ui.TestEntity;
import ui.TestLevel;
import ui.UIException;
import ui.manager.GroupManager;
import ui.manager.LabelManager;
import ui.manager.ObjectManager;

public class AssetManagerTester extends Application {

    @Override
    public void start(Stage testStage) {
        alternateStart(testStage);
        //AssetManager temp = new AssetManager();
        //temp.show();
        /**
         *

         UserCreatedTypesPane userCreatedTypesPane = new UserCreatedTypesPane();
         DefaultTypesPane entityTypePane = new DefaultTypesPane(userCreatedTypesPane);
         Viewer viewer = new Viewer(200, 500);
         HBox hBox = new HBox();
         hBox.getChildren().addAll(viewer, entityTypePane, userCreatedTypesPane);
         entityTypePane.setPrefSize(300, 600);
         Scene scene = new Scene(hBox, 600, 600);
         //scene.getStylesheets().add("default.css");
         testStage.setScene(scene);
         testStage.show();
         */
        //Stage viewerStage = new Stage();
        //Scene viewerScene = new Scene(new Viewer(1000, 1000), 500, 500);
        //viewerStage.setScene(viewerScene);
        //viewerStage.show();
    }

    private void alternateStart(Stage stage){
        Viewer viewer = new Viewer(500, 500);
        viewer.setMinWidth(400);
        viewer.setMinHeight(300);
        UserCreatedTypesPane userCreatedTypesPane = new UserCreatedTypesPane();
        userCreatedTypesPane.setMinWidth(200);
        DefaultTypesPane defaultTypesPane = new DefaultTypesPane(userCreatedTypesPane);
        defaultTypesPane.setMinWidth(200);
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
            GroupManager testCreateGroup = new GroupManager(manager);

            BorderPane borderPane = new BorderPane();
            HBox hBox = new HBox();
            hBox.getChildren().addAll(defaultTypesPane, userCreatedTypesPane);
            borderPane.setRight(hBox);
            borderPane.setCenter(viewer);
            HBox hBox1 = new HBox();
            hBox1.getChildren().addAll(testPaneLvl, testPaneIns, testPaneObj);
            borderPane.setBottom(hBox1);

            Scene scene = new Scene(borderPane);
            //scene.getStylesheets().add("default.css");
            stage.setScene(scene);
            stage.setHeight(700);
            stage.setWidth(1000);
            stage.show();

        } catch (UIException e) {
            ErrorBox errorbox = new ErrorBox("Test", e.getMessage());
            errorbox.display();
        }


    }

    public static void main(String[] args) {
        launch(args);
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
        a.getPropertyMap().put("Group", "Enemies");
        b.getPropertyMap().put("Group", "Platforms");
        c.getPropertyMap().put("Group", "Enemies");
        d.getPropertyMap().put("Group", "Platforms");
        return f;
    }

}