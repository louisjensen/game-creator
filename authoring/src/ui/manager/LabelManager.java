package ui.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class keeps track of valid labels for Object Type, Group, etc. in Authoring Environment
 */
public class LabelManager {

    private Map<String, ObservableList<String>> myLabelGroups;

    public LabelManager() {
        myLabelGroups = new HashMap<>();
        myLabelGroups.put("Object", FXCollections.observableArrayList(new ArrayList<>()));
        myLabelGroups.put("Group", FXCollections.observableArrayList(new ArrayList<>()));
    }

    public void addLabel(String labelGroup, String labelName) {
        if (myLabelGroups.containsKey(labelGroup) && !myLabelGroups.get(labelGroup).contains(labelName))
            myLabelGroups.get(labelGroup).add(labelName);
    }

    public void removeLabel(String labelGroup, String labelName) {
        if (myLabelGroups.containsKey(labelGroup))
            myLabelGroups.get(labelGroup).remove(labelName);
    }

    public ObservableList<String> getLabels(String labelGroup) {
        return (myLabelGroups.containsKey(labelGroup)) ?
                FXCollections.unmodifiableObservableList(myLabelGroups.get(labelGroup)) : null;
    }
}
