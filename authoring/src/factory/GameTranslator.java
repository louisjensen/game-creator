package factory;

import engine.external.Entity;
import engine.external.Level;
import engine.external.actions.Action;
import engine.external.actions.SoundAction;
import engine.external.actions.TimerAction;
import engine.external.actions.ValueAction;
import engine.external.component.CollisionComponent;
import engine.external.component.Component;
import engine.external.component.SoundComponent;
import engine.external.component.TimerComponent;
import engine.external.component.ValueComponent;
import engine.external.conditions.Condition;
import engine.external.events.BottomCollisionEvent;
import engine.external.events.Event;
import engine.external.events.LeftCollisionEvent;
import engine.external.events.RightCollisionEvent;
import engine.external.events.TopCollisionEvent;
import runner.external.Game;
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.EntityField;
import ui.LevelField;
import ui.manager.ObjectManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Harry Ross
 */
public class GameTranslator {

    private ObjectManager myObjectManager;

    public GameTranslator(ObjectManager objectManager) {
        myObjectManager = objectManager;
    }

    public Game translate() {
        Game translatedGame = new Game();
        Map<Entity, String> typeMap = new HashMap<>();

        for (AuthoringLevel authLevel : myObjectManager.getLevels()) {
            translatedGame.addLevel(translateLevel(authLevel));
        }
        for (AuthoringEntity key : myObjectManager.getTypeMap().keySet()) {
            Entity translatedType = translateEntity(key);
            typeMap.put(translatedType, myObjectManager.getTypeMap().get(key));
        }
        translatedGame.addUserCreatedTypes(typeMap);
        return translatedGame;
    }

    private Level translateLevel(AuthoringLevel authLevel) {
        Level newLevel = new Level();

        newLevel.setBackground(authLevel.getPropertyMap().get(LevelField.BACKGROUND)); // Level properties!!!
        newLevel.setLabel(authLevel.getPropertyMap().get(LevelField.LABEL));
        newLevel.setMusic(authLevel.getPropertyMap().get(LevelField.MUSIC));
        newLevel.setWidth(Double.parseDouble(authLevel.getPropertyMap().get(LevelField.WIDTH)));
        newLevel.setHeight(Double.parseDouble(authLevel.getPropertyMap().get(LevelField.HEIGHT)));

        for (AuthoringEntity authEntity : authLevel.getEntities()) { // Level entities!!!
            newLevel.addEntity(translateEntity(authEntity));
            for (Event event : myObjectManager.getEvents(authEntity.getPropertyMap().get(EntityField.LABEL))) // Events!!!
                newLevel.addEvent(event);
        }
        return newLevel;
    }

    private Entity translateEntity(AuthoringEntity authEntity) {
        Entity basisEntity = new Entity();

        for (EntityField field : EntityField.values()) {
            if (authEntity.getPropertyMap().containsKey(field) && !field.equals(EntityField.CAMERA) && !field.equals(EntityField.EVENTS) && !field.equals(EntityField.IMAGE)) {
                    addComponent(field, basisEntity, authEntity);
                }
            else if (field.equals(EntityField.CAMERA) && Boolean.parseBoolean(authEntity.getPropertyMap().get(EntityField.CAMERA))) { // main character found
                //basisEntity.addComponent(new LivesComponent());
                //basisEntity.addComponent(new ScoreComponent(0.0));
            }
        }
        // TODO do special image/sound thing

        //TODO group events?
        for (AuthoringLevel authLevel : myObjectManager.getLevels()) {
            for (AuthoringEntity entity : authLevel.getEntities()) {
                for (Event event : myObjectManager.getEvents(entity.getPropertyMap().get(EntityField.LABEL))) {
                    if (event.getClass().equals(BottomCollisionEvent.class) || event.getClass().equals(LeftCollisionEvent.class) ||
                    event.getClass().equals(RightCollisionEvent.class) || event.getClass().equals(TopCollisionEvent.class)) {
                    basisEntity.addComponent(new CollisionComponent(true));
                    }        // TODO add components from Events? (Collision, ValueComponent)
                    for (Action action : (List<Action>) event.getEventInformation().get(Action.class)) {
                        if (action.getClass().equals(SoundAction.class) && !basisEntity.hasComponents(SoundComponent.class))
                            basisEntity.addComponent(new SoundComponent("")); //TODO
                        if (action.getClass().equals(TimerAction.class) && !basisEntity.hasComponents(TimerComponent.class))
                            basisEntity.addComponent(new TimerComponent(0));
                        if (action.getClass().equals(ValueAction.class) && !basisEntity.hasComponents(ValueComponent.class))
                            basisEntity.addComponent(new ValueComponent<>(0));
                    }
                    for (Condition condition : (List<Condition>) event.getEventInformation().get(Condition.class)) {
                        //TODO what even
                    }
                }
            }
        }
        return basisEntity;
    }

    private void addComponent(EntityField field, Entity basisEntity, AuthoringEntity authEntity) {
        Class<?> dataType = field.getComponentDataType();
        Class<? extends Component> componentClass = field.getComponentClass();

        if (authEntity.getPropertyMap().containsKey(field)) {
            String newValue = authEntity.getPropertyMap().get(field);

            if (basisEntity.hasComponents(componentClass)) {
                if (dataType.equals(String.class))
                    ((Component) basisEntity.getComponent(componentClass)).setValue(newValue);
                else if (dataType.equals(Double.class))
                    ((Component) basisEntity.getComponent(componentClass)).setValue(Double.parseDouble(newValue));
                else if (dataType.equals(Boolean.class))
                    ((Component) basisEntity.getComponent(componentClass)).setValue(Boolean.parseBoolean(newValue));
            } else {
                try {
                    if (dataType.equals(String.class))
                        basisEntity.addComponent(componentClass.getConstructor(dataType).newInstance(newValue));
                    else if (dataType.equals(Double.class))
                        basisEntity.addComponent(componentClass.getConstructor(dataType).newInstance(Double.parseDouble(newValue)));
                    else if (dataType.equals(Boolean.class))
                        basisEntity.addComponent(componentClass.getConstructor(dataType).newInstance(Boolean.parseBoolean(newValue)));
                } catch (Exception e) {
                    System.out.println("Error translating components");
                }
            }
        }
    }

}
