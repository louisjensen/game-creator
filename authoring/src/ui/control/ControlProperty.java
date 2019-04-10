package ui.control;

import ui.Propertable;
import ui.UIException;
import ui.manager.LabelManager;

/**
 * @author Harry Ross
 */
public interface ControlProperty {

    void populateValue(Propertable prop, Enum type, String newValue, LabelManager labels);

    void setAction(Propertable propertable, Enum label, String action) throws UIException;
}
