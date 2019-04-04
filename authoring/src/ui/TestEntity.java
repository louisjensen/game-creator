package ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEntity implements Propertable {

    private Map<String, String> myPropertyMap;

    private static final List<String> PROP_VAR_NAMES =
            Arrays.asList("X", "Y", "XScale", "YScale", "Facing", "Label", "Group", "Image");

    public TestEntity() {
        myPropertyMap = new HashMap<>();
        for (String name : PROP_VAR_NAMES)
            myPropertyMap.put(name, null);
        myPropertyMap.put("X", "0.0");
        myPropertyMap.put("Y", "0.0");
        myPropertyMap.put("XScale", "1.0");
        myPropertyMap.put("YScale", "1.0");

        myPropertyMap.put("Label", "object1"); //TODO These would not be here in real version
        myPropertyMap.put("Image", "sprite1.png");
    }

    public Map<String, String> getPropertyMap() {
        return myPropertyMap;
    }
}
