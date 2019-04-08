package ui.control;

import ui.Propertable;
import ui.UIException;
import ui.manager.LabelManager;

/**
 * @author Harry Ross
 */
public interface ControlProperty {

    void populateValue(String type, String newValue, LabelManager labels);

    void setAction(Propertable propertable, String label, String action) throws UIException;
}
