package ui;

import java.util.Map;

/**
 * @author Harry Ross
 */
public interface Propertable {

    Map<Enum, String> getPropertyMap();

    Class<? extends Enum> getEnumClass();

}
