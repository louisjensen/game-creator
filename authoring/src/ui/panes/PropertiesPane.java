package ui.panes;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Enumeration;
import java.util.ResourceBundle;

class PropertiesPane extends TitledPane {

    private String myObjectLabel; //Or use hte GameObject itself depending on the backend

    private static final String PROP_TYPE_EXT = " Properties";
    private static final String PROP_TYPE_FILE = "object_properties_list";

    PropertiesPane(String type, String typeName) {
        GridPane optionsPane = createPropertiesGrid(typeName);
        this.setText(type + PROP_TYPE_EXT);
        this.setContent(optionsPane);
    }

    private GridPane createPropertiesGrid(String typeName) {
        GridPane gridlist = new GridPane();

        ResourceBundle bundle = ResourceBundle.getBundle(PROP_TYPE_FILE);
        Enumeration propNames = bundle.getKeys();

        while (propNames.hasMoreElements()) {
            String name = (String)propNames.nextElement();
            String value = bundle.getString(name);
            gridlist.add(createProperty(name, value), 0, gridlist.getRowCount());
        }

        //get text fields from resource bundle, populate current values from GameObject
        return gridlist;
    }

    private VBox createProperty(String name, String value) {
        VBox newProp = new VBox();

        Label propName = new Label(name);
        propName.getStyleClass().add("prop-label");

        //parse value for colon
        //create new XXXXX, populate with value rom GameObject
        //set up bind

        newProp.getChildren().addAll(propName, populateOptions(propName.getText()));

        return newProp;
    }

    private HBox populateOptions(String label) {
        HBox propOptions = new HBox();
        propOptions.getChildren().add(new TextField());

        return propOptions;
    }
}
