package ui;

import javafx.collections.ObservableMap;

/**
 * @author Harry Ross
 */
public interface Propertable {

    ObservableMap<Enum, String> getPropertyMap();

    Class<? extends Enum> getEnumClass();

}
