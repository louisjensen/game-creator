package ui.manager;

import events.Event;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import ui.AuthoringEntity;
import ui.EntityField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Harry Ross
 */
public class ObjectManager {

    private Set<AuthoringEntity> myEntities;
    private Map<String, ObservableList<Event>> myEventMap;
    private LabelManager myLabelManager;

    /**
     * Class that keeps track of every single instance of an Entity, across Levels, for the purposes of authoring environment
     */
    public ObjectManager(LabelManager labelManager) {
        myEntities = new HashSet<>();
        myLabelManager = labelManager;
        myEventMap = new HashMap<>();
        labelManager.getLabels(EntityField.GROUP).addListener((ListChangeListener<? super String>) change -> groupRemoveAction(change));
    }

    public void addEntity(AuthoringEntity entity) {
        myEntities.add(entity);
        myLabelManager.addLabel(EntityField.LABEL, entity.getPropertyMap().get(EntityField.LABEL));
        myEventMap.put(entity.getPropertyMap().get(EntityField.LABEL), FXCollections.observableArrayList(new ArrayList<>()));
    }

    //TODO remove entity??

    public void propagate(String objectLabel, Enum property, String newValue) {
        for (AuthoringEntity entity : myEntities) {
            if (entity.getPropertyMap().get(EntityField.LABEL).equals(objectLabel)) { // Match found
                entity.getPropertyMap().put(property, newValue);
                myLabelManager.addLabel(EntityField.LABEL, newValue);
            }
        }
        if (property.equals(EntityField.LABEL))
            myLabelManager.removeLabel(EntityField.LABEL, objectLabel); // Remove old label from LabelManager if a label was just propagated
    }

    private void groupRemoveAction(ListChangeListener.Change<? extends String> change) {
        change.next();
        String str = null;

        if (change.wasReplaced())
            str = change.getAddedSubList().get(0);

        if (change.wasReplaced() || change.wasRemoved()) {
            for (AuthoringEntity entity : myEntities) {
                if (entity.getPropertyMap().get(EntityField.GROUP) != null &&
                        entity.getPropertyMap().get(EntityField.GROUP).equals(change.getRemoved().get(0)))
                    entity.getPropertyMap().put(EntityField.GROUP, str);
            }
        }
    }

    LabelManager getLabelManager() {
        return myLabelManager;
    }

    public ObservableList<Event> getEvents(String objectType) {
        return myEventMap.get(objectType);
    }
}
