package ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLevel implements Propertable {

    private Map<String, String> myPropertyMap;

    private static final List<String> PROP_VAR_NAMES =
            Arrays.asList("Height", "Width", "Background", "Music");
    private static final Double DEFAULT_ROOM_SIZE = 500.0;

    public TestLevel() {
        myPropertyMap = new HashMap<>();
        for (String name : PROP_VAR_NAMES)
            myPropertyMap.put(name, null);
        myPropertyMap.put("Height", DEFAULT_ROOM_SIZE.toString());
        myPropertyMap.put("Width", DEFAULT_ROOM_SIZE.toString());
    }

    public Map<String, String> getPropertyMap() {
        return myPropertyMap;
    }
}
