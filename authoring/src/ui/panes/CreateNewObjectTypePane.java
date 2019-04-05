package ui.panes;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;


public class CreateNewObjectTypePane extends Stage {
    private GridPane myGridPane;
    private ResourceBundle myWindowResources;
    private ResourceBundle myTypeResources;
    private String[] myTypes;

    private static final int STAGE_HEIGHT = 400;
    private static final int STAGE_WIDTH = 500;
    private static final String STYLE_SHEET = "default.css";
    private static final String WINDOW_RESOURCES = "new_object_window";
    private static final String TYPE_RESOURCES = "default_entity_type";

    public CreateNewObjectTypePane(){
        myGridPane = new GridPane();
        myWindowResources = ResourceBundle.getBundle(WINDOW_RESOURCES);
        myTypeResources = ResourceBundle.getBundle(TYPE_RESOURCES);
        createContent();
        initializeAndDisplayStage();
    }

    private void createContent() {
        createAndAddLabel(myWindowResources.getString("Label1"));
        createAndAddTextField();
        createAndAddLabel(myWindowResources.getString("Label2"));
        createAndAddTypeOfOnDropDown();
        createAndAddLabel(myWindowResources.getString("Label3"));
        createAndAddBasedOnDropDown();
    }

    private void createAndAddBasedOnDropDown() {
        ComboBox comboBox = new ComboBox();
        myGridPane.add(comboBox, 1, myGridPane.getRowCount());
    }

    private void createAndAddLabel(String s) {
        Label label = new Label(s);
        int numRows = myGridPane.getRowCount();
        myGridPane.add(label, 0, numRows+1);

    }

    private void createAndAddTextField(){
        TextField textField = new TextField();
        myGridPane.add(textField, 1, myGridPane.getRowCount());
    }

    private void createAndAddTypeOfOnDropDown(){
        String[] types = myTypeResources.getString("Tabs").split(",");
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(types);
        myGridPane.add(comboBox, 1, myGridPane.getRowCount());
    }
    private void initializeAndDisplayStage() {
        this.setWidth(STAGE_WIDTH);
        this.setHeight(STAGE_HEIGHT);
        TitledPane titledPane = new TitledPane();
        titledPane.setText(myWindowResources.getString("Title"));
        titledPane.setContent(myGridPane);
        titledPane.setCollapsible(false);
        Scene scene = new Scene(titledPane);
        scene.getStylesheets().add(STYLE_SHEET);
        this.setScene(scene);
        this.showAndWait();
    }
}
