package ui.panes;
import engine.external.actions.Action;
import engine.external.actions.HeightAction;
import engine.external.actions.NumericAction;
import engine.external.conditions.Condition;
import engine.external.conditions.ConditionType;
import engine.external.events.Event;
import events.EventFactory;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.UIException;

import java.lang.reflect.Constructor;
import java.util.*;

public class EventOptionsPane extends VBox {
    private static final String CONTROLS = "event_controls";
    private static final String DISPLAY = "event_classes";

    private static final ResourceBundle DISPLAY_RESOURCES = ResourceBundle.getBundle(DISPLAY);

    private EventFactory myEventFactory = new EventFactory();
    private Map<String,StringProperty> myEventOptionsListener = new HashMap<>();
    private Map<String,StringProperty> myActionOptionsListener = new HashMap<>();

    private static final String ERROR_PACKAGE_NAME = "error_messages";
    private static final String ACTION_PACKAGE_PREFIX = "engine.external.actions.";
    private static final String COMPONENT_PACKAGE_PREFIX = "engine.external.component.";

    private static final String COLLIDEE = "COLLIDEE";
    private static final String COMPARATOR = "COMPARATOR";
    private static final String COMPONENT = "COMPONENT";
    private static final String ACTION = "ACTION";
    private static final String MODIFIER = "MODIFIER";
    private static final String VALUE = "VALUE";
    private static final String ACTION_KEY = "Action";
    private static final String COMPONENT_KEY = "Component";
    private static final ResourceBundle myErrors = ResourceBundle.getBundle(ERROR_PACKAGE_NAME);
    private static final String ERROR_ONE_KEY = "InvalidEvent";
    private static final String ERROR_TWO_KEY = "InvalidAction";
    public EventOptionsPane(){
        this.getStylesheets().clear();
//        this.getStylesheets().add("events_pop_up.css");
//        this.getStyleClass().add("event-pane");
        this.setMinSize(500,140);
        this.setMaxSize(500,140);
        this.setSpacing(30);
    }

    void displayEventOptions(String eventName) {
        this.getChildren().clear();
        myEventOptionsListener.clear();
        HBox eventOptionsPanel = new HBox();
        myEventFactory.factoryDelegator(CONTROLS,eventName,eventOptionsPanel,myEventOptionsListener);
        this.getChildren().add(eventOptionsPanel);
        HBox actionOptionsPanel = new HBox();
        myEventFactory.factoryDelegator(CONTROLS,ACTION_KEY,actionOptionsPanel,myActionOptionsListener);
        this.getChildren().add(actionOptionsPanel);
    }

    Event saveEvent(String entityName, String eventName){
        Event userMadeEvent = new Event(entityName);
        try {
            userMadeEvent = createCollisionEvent(entityName,eventName);
        }
        catch (Exception notCollisionEvent) {
            try {
                Condition userMadeCondition = createGeneralCondition();
                userMadeEvent.addConditions(userMadeCondition);
            }
            catch (Exception notGeneralEvent) {
                UIException myException = new UIException(myErrors.getString(ERROR_ONE_KEY));
                myException.displayUIException();
            }
        }
        Action userMadeAction = null;
        try {
            userMadeAction = createGeneralAction();
        }
        catch(Exception e){
            UIException myException = new UIException(myErrors.getString(ERROR_TWO_KEY));
            myException.displayUIException();
        }
        userMadeEvent.addActions(userMadeAction);
       return userMadeEvent;
    }


    private Event createCollisionEvent(String entityName, String eventName) throws Exception {
        Class eventClass = null;
        Constructor eventConstructor;
        Object[] eventConstructorParameters;
        eventClass = Class.forName(DISPLAY_RESOURCES.getString(eventName));
        eventConstructor = eventClass.getConstructor(String.class,String.class);
        eventConstructorParameters = new Object[2];
        eventConstructorParameters[0] = entityName; //
        eventConstructorParameters[1] = myEventOptionsListener.get(COLLIDEE).getValue();
        return (Event)eventConstructor.newInstance(eventConstructorParameters);
    }

    private Condition createGeneralCondition() throws Exception{
        Class conditionClass;
        Constructor conditionConstructor;
        Object[] conditionConstructorParameters;
        ConditionType myConditionType = ConditionType.valueOf(myEventOptionsListener.get(COMPARATOR).getValue().replaceAll(" ",""));
        conditionClass = myConditionType.getClassName();
        conditionConstructor = conditionClass.getConstructor(Class.class,Double.class);
        conditionConstructorParameters = new Object[2];
        conditionConstructorParameters[0] = Class.forName(COMPONENT_PACKAGE_PREFIX + myEventOptionsListener.get(COMPONENT).getValue() + COMPONENT_KEY);
        conditionConstructorParameters[1] = Double.parseDouble(myEventOptionsListener.get(VALUE).getValue());
        Condition userMadeCondition =  (Condition)conditionConstructor.newInstance(conditionConstructorParameters);
        return userMadeCondition;
    }

    private Action createGeneralAction() {
        Class actionClass = null;
        Constructor actionConstructor = null;
        Object[] actionConstructorParameters = null;
        try {
            actionClass = Class.forName(ACTION_PACKAGE_PREFIX + myActionOptionsListener.get(ACTION).getValue()+ ACTION_KEY);
            actionConstructor = actionClass.getConstructor(NumericAction.ModifyType.class, Double.class);
            actionConstructorParameters = new Object[2];
            actionConstructorParameters[0] = NumericAction.ModifyType.valueOf(myActionOptionsListener.get(MODIFIER).getValue());
            actionConstructorParameters[1] = Double.parseDouble(myActionOptionsListener.get(VALUE).getValue());
            return (Action) actionConstructor.newInstance(actionConstructorParameters);
        }
        catch(Exception nonNumericAction){
            try {
                actionConstructor = actionClass.getConstructor(String.class); //@TODO handle different action constructors
                actionConstructorParameters = new Object[1];
                actionConstructorParameters[0] = myActionOptionsListener.get(VALUE).getValue();
                Action myStringAction = (Action) actionConstructor.newInstance(actionConstructorParameters);
                return myStringAction;
            }
            catch(Exception e){
                UIException myException = new UIException(myErrors.getString(ERROR_TWO_KEY));
                myException.displayUIException();
            }
        }
        return new HeightAction(NumericAction.ModifyType.RELATIVE,0.0);
    }
}
