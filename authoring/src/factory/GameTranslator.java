package factory;

import engine.external.Entity;
import engine.external.Level;
import engine.external.actions.Action;
import engine.external.component.Component;
import engine.external.component.GroupComponent;
import engine.external.component.HeightComponent;
import engine.external.component.NameComponent;
import engine.external.component.SpriteComponent;
import engine.external.component.WidthComponent;
import engine.external.component.XPositionComponent;
import engine.external.component.YPositionComponent;
import engine.external.component.ZPositionComponent;
import engine.external.conditions.Condition;
import engine.external.events.Event;
import runner.external.Game;
import runner.external.GameCenterData;
import ui.AuthoringEntity;
import ui.AuthoringLevel;
import ui.EntityField;
import ui.LevelField;
import ui.manager.ObjectManager;

import java.util.HashMap;
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
            if (authEntity.getPropertyMap().containsKey(field) && !field.equals(EntityField.EVENTS) && !field.equals(EntityField.IMAGE)) {
                    addComponent(field, basisEntity, authEntity);
                }
        }
        // TODO do special image/sound thing
        // TODO add components from Events? (Collision, ValueComponent)

        for (AuthoringLevel authLevel : myObjectManager.getLevels()) {
            for (AuthoringEntity entity : authLevel.getEntities()) {
                for (Event event : myObjectManager.getEvents(entity.getPropertyMap().get(EntityField.LABEL))) {
                    /*for (Action action : event.getActions()) {

                    }
                    for (Condition condition : event.getConditions()) {

                    }*/
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
