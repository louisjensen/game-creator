package ui;

import engine.external.Entity;
import engine.external.component.*;
import events.Event;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import ui.manager.ObjectManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Harry Ross
 */
public class AuthoringEntity implements Propertable {

    private ObservableMap<Enum, String> myPropertyMap;
    private ObjectManager myObjectManager;
    private Entity myBackingEntity;

    private static final List<? extends Enum> PROP_VAR_NAMES = Arrays.asList(EntityField.values());

    private AuthoringEntity() { // Initialize default property map
        myPropertyMap = FXCollections.observableHashMap();
        for (Enum name : PROP_VAR_NAMES)
            myPropertyMap.put(name, null);
        myPropertyMap.put(EntityField.X, "0.0");
        myPropertyMap.put(EntityField.Y, "0.0");
        myPropertyMap.put(EntityField.XSCALE, "1.0");
        myPropertyMap.put(EntityField.YSCALE, "1.0");
    }

    public AuthoringEntity(String label, ObjectManager manager) { // Create new type of AuthoringEntity from scratch
        this();
        myObjectManager = manager;
        myBackingEntity = new Entity(); // Brand new backing Entity
        myPropertyMap.put(EntityField.LABEL, label);
        addPropertyListeners();
        myObjectManager.addEntity(this);
    }

    public AuthoringEntity(Entity basis, ObjectManager manager) { // Create new AuthoringEntity type from Entity
        this();
        myBackingEntity = basis;
        myObjectManager = manager;
        myPropertyMap.put(EntityField.LABEL, (String) basis.getComponent(NameComponent.class).getValue());
        myPropertyMap.put(EntityField.IMAGE, (String) basis.getComponent(SpriteComponent.class).getValue());
        myPropertyMap.put(EntityField.X, ("" + (basis.getComponent(XPositionComponent.class).getValue())));
        myPropertyMap.put(EntityField.Y, ("" + (basis.getComponent(YPositionComponent.class).getValue())));
        myPropertyMap.put(EntityField.XSCALE, ("" + (basis.getComponent(WidthComponent.class).getValue()))); //TODO update these for new components, add Group
        myPropertyMap.put(EntityField.YSCALE, ("" + (basis.getComponent(HeightComponent.class).getValue())));
        addPropertyListeners();
        myObjectManager.addEntity(this);
    }

    public AuthoringEntity(AuthoringEntity copyBasis) { // Create new AuthoringEntity instance from pre-existing type
        this();
        myObjectManager = copyBasis.myObjectManager;
        myPropertyMap.put(EntityField.LABEL, copyBasis.myPropertyMap.get(EntityField.LABEL));
        myPropertyMap.put(EntityField.IMAGE, copyBasis.myPropertyMap.get(EntityField.IMAGE));
        myPropertyMap.put(EntityField.GROUP, copyBasis.myPropertyMap.get(EntityField.GROUP));
        myPropertyMap.put(EntityField.X, copyBasis.myPropertyMap.get(EntityField.X));
        myPropertyMap.put(EntityField.Y, copyBasis.myPropertyMap.get(EntityField.Y));
        myPropertyMap.put(EntityField.XSCALE, copyBasis.myPropertyMap.get(EntityField.XSCALE));
        myPropertyMap.put(EntityField.YSCALE, copyBasis.myPropertyMap.get(EntityField.YSCALE));
        myBackingEntity = copyEntity(copyBasis);
        addPropertyListeners();
        myObjectManager.addEntity(this);
    }

    private Entity copyEntity(AuthoringEntity copyBasis) {
        Entity newCopy = new Entity();
        Entity oldCopy = copyBasis.myBackingEntity;
        // TODO copy backing entity to new reference
        //for (Component comp : oldCopy.hasComponents())

        return newCopy;
    }

    private void addPropertyListeners() {
        myPropertyMap.addListener((MapChangeListener<Enum, String>) change ->
           propagateChanges(change.getKey(),  change.getValueRemoved(), change.getValueAdded()));
    }

    private void propagateChanges(Enum key, String oldVal, String newVal) { // At this point value is assumed valid or untaken label
        if (key.equals(EntityField.LABEL)) // If we're changing the label, preserve old label for propagation purposes
            myObjectManager.propagate(oldVal, key, newVal);
        else if (key.equals(EntityField.IMAGE) || key.equals(EntityField.GROUP)) // If we're changing the Image or Group, just do it
            myObjectManager.propagate(myPropertyMap.get(EntityField.LABEL), key, newVal);
    }

    public Map<Enum, String> getPropertyMap() {
        return myPropertyMap;
    }

    @Override
    public Class<? extends Enum> getEnumClass() {
        return EntityField.class;
    }

    public ObservableList<Event> getEvents() {
        return myObjectManager.getEvents(this.myPropertyMap.get(EntityField.LABEL));
    }

    public Entity getBackingEntity() {
        return myBackingEntity;
    }
}
