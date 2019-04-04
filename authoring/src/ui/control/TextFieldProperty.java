package ui.control;

import javafx.scene.control.TextField;
import ui.Propertable;
import ui.manager.LabelManager;

public class TextFieldProperty extends TextField implements ControlProperty {

    @Override
    public void populateValue(String type, String newVal, LabelManager labels) {
        this.setText(newVal);
    }

    @Override
    public void setAction(Propertable propertable, String propLabel) {
        this.focusedProperty().addListener(e -> checkAndUpdateProperty(propertable, propLabel, this.getText()));
    }

    private void checkAndUpdateProperty(Propertable prop, String propLabel, String value) {
        //TODO add reversion when it's a bad value
        prop.getPropertyMap().put(propLabel, value);
    }
}
