package ui.panes;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ui.Propertable;
import ui.TestEntity;
import ui.UIException;
import ui.control.ControlProperty;
import ui.manager.LabelManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.ResourceBundle;

class PropertiesPane extends TitledPane {

    private Propertable myProp;
    private String myPropFile;
    private LabelManager myLabelManager;

    private static final String PROP_TYPE_EXT = " Properties";

    PropertiesPane(String propOf, Propertable prop, String propResource, LabelManager labelManager) throws UIException {
        myProp = prop;
        myPropFile = propResource;
        myLabelManager = labelManager;
        this.setText(propOf + PROP_TYPE_EXT);
        this.setContent(createPropertiesGrid());
    }

    private GridPane createPropertiesGrid() throws UIException {
        GridPane gridlist = new GridPane();
        ResourceBundle bundle = ResourceBundle.getBundle(myPropFile);
        Enumeration propNames = bundle.getKeys(); //TODO make ordered
        while (propNames.hasMoreElements()) {
            String name = (String)propNames.nextElement();
            String value = bundle.getString(name);
            gridlist.add(createProperty(name, value), 0, gridlist.getRowCount());
        }
        return gridlist;
    }

    private VBox createProperty(String name, String info) throws UIException { //TODO make more readable
        VBox newProp = new VBox();
        Label propName = new Label(name);
        propName.getStyleClass().add("prop-label");
        String[] sep = info.split(":");
        try {
            Class<?> clazz = Class.forName(sep[0]);
            Constructor<?> constructor = (sep[1].equals("none")) ?
                    clazz.getConstructor() : clazz.getConstructor(String.class);
            ControlProperty instance = (sep[1].equals("none")) ?
                    (ControlProperty) constructor.newInstance() : (ControlProperty) constructor.newInstance(sep[1]);
            newProp.getChildren().addAll(propName, (Node) instance);
            instance.populateValue(name, myProp.getPropertyMap().get(name), myLabelManager);
            instance.setAction(myProp, sep[2]);
        } catch (Exception e) {
            throw new UIException("Error creating properties controls");
        }
        return newProp;
    }
}
