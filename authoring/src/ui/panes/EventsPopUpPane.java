package ui.panes;
import actions.Action;
import actions.NumericAction;
import conditions.Condition;
import conditions.ConditionType;
import events.Event;
import events.EventFactory;
import events.EventType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import ui.manager.Refresher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class EventsPopUpPane extends Stage {
    private static final List<String> EVENT_CLASSES = Arrays.asList("COLLISION","CONDITIONAL","TIMER", "CONTROL");
    private static final String RESOURCES = "event_controls";
    private static final String RESOURCES_SYMBOL = "::";
    private static final ResourceBundle EVENT_CONTROLS = ResourceBundle.getBundle(RESOURCES);

    private static final ResourceBundle DISPLAY_TO_EVENTS = ResourceBundle.getBundle("event_classes");




    private EventFactory myEventFactory = new EventFactory();
    private Scene myScene;
    private VBox eventTypeChooser;
    private ArrayList<StringProperty> mySneakyEventMaker = new ArrayList<>();
    private ArrayList<StringProperty> mySneakyActionMaker = new ArrayList<>();
    private ChoiceBox<String> myEvents;
    public EventsPopUpPane(ObservableMap<Enum, String> uniqueComponents,
                           ObservableList<Event> myEntityEvents, String myEntityName, Refresher myEventsRefresher){
        eventTypeChooser = new VBox();
        eventTypeChooser.setMinSize(400,400);
        myEvents = new ChoiceBox<String>(FXCollections.observableArrayList(EventType.allDisplayNames));
        myEvents.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String number, String number2) {
                setEventControlWindow((String)number2);
            }
        });
        eventTypeChooser.getChildren().add(myEvents);
        eventTypeChooser.getChildren().add(new HBox());
        eventTypeChooser.getChildren().add(myEventFactory.createActionsOptions(mySneakyActionMaker));
        eventTypeChooser.getChildren().add(makeSaveButton(myEntityEvents, myEntityName,myEventsRefresher));
        myScene = new Scene(eventTypeChooser);
        myScene.getStylesheets().add("default.css");
        this.setScene(myScene);
        this.show();
    }
    private void setEventControlWindow(String eventName){
        HBox eventInputControls = new HBox();
        mySneakyEventMaker.clear();
        eventInputControls.setMinSize(300,300);
        for (String controlInformation: EVENT_CONTROLS.getString(eventName.replaceAll(" ","")).split("::")) {
            String methodName = controlInformation.substring(0,controlInformation.indexOf(","));
            String methodParameter = controlInformation.substring(controlInformation.indexOf(",") + 1);
            Class<?>[] myClazz = {String.class, ArrayList.class};
            Object[] myParams = {methodParameter,mySneakyEventMaker};
            try {
                Method m = myEventFactory.getClass().getDeclaredMethod(methodName, myClazz);
                Object addedOption = m.invoke(myEventFactory,myParams);
                eventInputControls.getChildren().add((Node)addedOption);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        eventTypeChooser.getChildren().set(1,eventInputControls);

    }
    private void eventPreferences(String utilityMethodName){

        VBox myVBox = new VBox();
        try {
                Method m = myEventFactory.getClass().getDeclaredMethod(utilityMethodName);
                Object addedOption = m.invoke(myEventFactory);
                myVBox.getChildren().add((Node)addedOption);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        myScene.setRoot(myVBox);
    }

    private Button makeSaveButton(ObservableList<Event> myEntityEvents, String myEntityName, Refresher myEventsRefresher ){
        Button saveButton = new Button("save");
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Event userMadeEvent = new Event("object1");

                Class eventClass = null;
                Constructor eventConstructor;
                Object[] eventConstructorParameters;


                try {
                    //Collision case
                    eventClass = Class.forName(DISPLAY_TO_EVENTS.getString(myEvents.getValue().replaceAll(" ","")));
                    eventConstructor = eventClass.getConstructor(String.class,String.class);
                    eventConstructorParameters = new Object[2];
                    eventConstructorParameters[0] = myEntityName; // @TODO Get Entity name here
                    eventConstructorParameters[1] = mySneakyEventMaker.get(0).getValue();
                    userMadeEvent = (Event)eventConstructor.newInstance(eventConstructorParameters);
                    //This should be the collidee name if eventCollision was selected
                 }
                catch (Exception e) {
                        try {
                            //GeneralCase
                            eventConstructor = eventClass.getConstructor(String.class);
                            eventConstructorParameters = new Object[1];
                            eventConstructorParameters[0] = myEntityName;
                            userMadeEvent = (Event)eventConstructor.newInstance(eventConstructorParameters);

                            Class conditionClass;
                            Constructor conditionConstructor;
                            Object[] conditionConstructorParameters;

                            ConditionType myConditionType = ConditionType.valueOf(mySneakyEventMaker.get(1).getValue().replaceAll(" ",""));
                            // Component < Value, need <
                            conditionClass = myConditionType.getClassName();
                            conditionConstructor = conditionClass.getConstructor(String.class,Double.class);// @TODO Handle different condition constructors

                            conditionConstructorParameters = new Object[2];
                            conditionConstructorParameters[0] = Class.forName(mySneakyEventMaker.get(0).getValue());
                            //need the class name of the component
                            conditionConstructorParameters[1] = Double.parseDouble(mySneakyEventMaker.get(2).getValue());
                            //need the value corresponding to it
                            Condition userMadeCondition =  (Condition)conditionConstructor.newInstance(conditionConstructorParameters);
                            userMadeEvent.addConditions(userMadeCondition);

                        }
                        catch (Exception e2) {
                            System.out.println("NO STRING OR ZERO PARAMETER CONSTRUCTOR OR EVENT DOESN'T EXIST");
                        }
                }
                try {

                    Class actionClass = Class.forName("actions." + mySneakyActionMaker.get(1).getValue()+ "Action");
                    Constructor actionConstructor = actionClass.getConstructor(NumericAction.ModifyType.class,Double.class); //@TODO handle different action constructors
                    Object[] actionConstructorParameters = {NumericAction.ModifyType.valueOf(mySneakyActionMaker.get(0).getValue()),
                            Double.parseDouble(mySneakyActionMaker.get(2).getValue())};
                    Action userMadeAction = (Action)actionConstructor.newInstance(actionConstructorParameters);
                    userMadeEvent.addActions(userMadeAction);

                }
                catch(Exception e){
                    System.out.println("ACTION CREATED INCORRECTLY");
                }
                myEntityEvents.add(userMadeEvent);
                myEventsRefresher.refresh();

            }
        });
        return saveButton;
    }

}
