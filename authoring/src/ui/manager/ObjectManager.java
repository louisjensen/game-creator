package ui.manager;

import ui.TestEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Harry Ross
 */
public class ObjectManager {

    private Set<TestEntity> myEntities;
    private LabelManager myManager;

    /**
     * Class that keeps track of every single instance of an Entity, across Levels, for the purposes of authoring environment
     */
    public ObjectManager(LabelManager labelManager) {
        myEntities = new HashSet<>();
        myManager = labelManager;
    }

    public void addEntity(TestEntity entity) {
        myEntities.add(entity);
        myManager.addLabel("Label", entity.getPropertyMap().get("Label"));
    }

    public void propagate(String objectLabel, String property, String newValue) {
        for (TestEntity entity : myEntities) {
            if (entity.getPropertyMap().get("Label").equals(objectLabel)) { // Match found
                entity.getPropertyMap().put(property, newValue);
                myManager.addLabel(property, newValue);
            }
        }
        if (property.equals("Label"))
            myManager.removeLabel(property, objectLabel); // Remove old label from LabelManager if a label was just propagated
    }

}
