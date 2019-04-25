package ui;

import engine.external.Entity;
import engine.external.component.*;
import engine.external.events.Event;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import ui.manager.ObjectManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Harry Ross
 */
public class AuthoringEntity implements Propertable {

    private ObservableMap<Enum, String> myPropertyMap;
    private ObjectManager myObjectManager;
    private Entity myBackingEntity;
    private List<String> myInteractionListing = new ArrayList<>();

    private AuthoringEntity() { // Initialize default property map
        myPropertyMap = FXCollections.observableHashMap();
        for (EntityField defaultField : EntityField.getDefaultFields()) {
            myPropertyMap.put(defaultField, defaultField.getDefaultValue());
        }
    }

    public AuthoringEntity(String label, ObjectManager manager) { // Create new type of AuthoringEntity from scratch
        this();
        myObjectManager = manager;
        myBackingEntity = new Entity(); // Brand new backing Entity
        myPropertyMap.put(EntityField.LABEL, label);
        addPropertyListeners();
        myObjectManager.addEntityType(this);
    }

    public AuthoringEntity(Entity basis, ObjectManager manager) { // Create new AuthoringEntity type from Entity
        this();
        myBackingEntity = basis;
        myObjectManager = manager;
        if (basis.hasComponents(NameComponent.class)) // TODO try to fix this
            myPropertyMap.put(EntityField.LABEL, (String) basis.getComponent(NameComponent.class).getValue());
        if (basis.hasComponents(SpriteComponent.class))
            myPropertyMap.put(EntityField.IMAGE, (String) basis.getComponent(SpriteComponent.class).getValue());
        if (basis.hasComponents(XPositionComponent.class))
            myPropertyMap.put(EntityField.X, ("" + (basis.getComponent(XPositionComponent.class).getValue())));
        if (basis.hasComponents(YPositionComponent.class))
            myPropertyMap.put(EntityField.Y, ("" + (basis.getComponent(YPositionComponent.class).getValue())));
        if (basis.hasComponents(ZPositionComponent.class))
            myPropertyMap.put(EntityField.Z, ("" + (basis.getComponent(ZPositionComponent.class).getValue())));
        if (basis.hasComponents(WidthComponent.class))
            myPropertyMap.put(EntityField.XSCALE, ("" + (basis.getComponent(WidthComponent.class).getValue()))); //TODO add Group & other things??
        if (basis.hasComponents(HeightComponent.class))
            myPropertyMap.put(EntityField.YSCALE, ("" + (basis.getComponent(HeightComponent.class).getValue())));
        addPropertyListeners();
        myObjectManager.addEntityType(this);
    }

    public AuthoringEntity(AuthoringEntity copyBasis, Entity backingEntity) { // Create new AuthoringEntity instance from pre-existing type
        this();
        myObjectManager = copyBasis.myObjectManager;
        for (EntityField commonField : EntityField.getCommonFields()) {
            if (copyBasis.myPropertyMap.containsKey(commonField))
                myPropertyMap.put(commonField, copyBasis.myPropertyMap.get(commonField));
        }
        myBackingEntity = backingEntity;
        addPropertyListeners();
        myObjectManager.addEntityInstance(this);
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
        else if (key.equals(EntityField.CAMERA))
            myObjectManager.flushCameraAssignment(this);
        else if (!((EntityField) key).isDefault()) {
            myObjectManager.propagate(myPropertyMap.get(EntityField.LABEL), key, newVal);
        }
    }

    public ObservableMap<Enum, String> getPropertyMap() {
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

    public List<String> getInteractionListing(){ return myInteractionListing;}


}
