package ui.control;

import javafx.scene.control.ComboBox;
import ui.EntityField;
import ui.Propertable;
import ui.manager.LabelManager;

/**
 * @author Harry Ross
 */
public class ComboBoxProperty extends ComboBox<String> implements ControlProperty {

    public ComboBoxProperty(String prompt) {
        this.setPromptText(prompt);
    }

    @Override
    public void populateValue(Enum type, String newVal, LabelManager labels) {
        this.itemsProperty().set(labels.getLabels((EntityField) type));
        this.setValue(newVal);
    }

    @Override
    public void setAction(Propertable propertable, Enum propLabel, String action) {
        this.setOnAction(e -> updateProperty(propertable, propLabel, this.getValue()));
    }

    private void updateProperty(Propertable prop, Enum propLabel, String value) {
        prop.getPropertyMap().put(propLabel, value);
    }
}
