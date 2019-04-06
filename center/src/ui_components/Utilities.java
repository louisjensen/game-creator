package ui_components;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Utilities {

    public static String getValue(ResourceBundle bundle, String key) {
        try {
            return bundle.getString(key);
        }
        catch (MissingResourceException e) {
            return "error";
        }
    }

}
