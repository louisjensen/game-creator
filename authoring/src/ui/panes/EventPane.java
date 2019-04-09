package ui.panes;
import events.Event;
import events.EventFactory;
import events.EventType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.UIException;
import ui.Utility;
import ui.manager.Refresher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EventPane extends Stage {

    private static final String EVENT_PARAMETERS = "event_parameters";
    private static final String EVENT_CLASS_NAME = "event_classes";
    private static final String EVENT_CONSTRUCTORS = "event_constructors";
    private static final String ACTION_LISTING = "actions_display";
    private static final String GET_VALUE_METHOD = "getValue";
    private static final String GET_TEXT_METHOD = "getText";
    private static final String INVALID_EVENT_EXCEPTION = "This event is not accessible to users";

    private String myEventName;
    private String myClassName;
    private EventFactory myEventFactory = new EventFactory();
    private VBox myEventParameters = new VBox();
    private Object[] eventParameters;
    VBox myEventDisplay;

    public EventPane(String eventDisplayName, String actorName,
                     ObservableList<Event> myEvents, Refresher refreshEventOptions) throws UIException{
        myEventName = eventDisplayName.replaceAll(" ","");
        myClassName = ResourceBundle.getBundle(EVENT_CLASS_NAME).getString(myEventName);
        
        String eventDisplayOptions = ResourceBundle.getBundle(EVENT_PARAMETERS).getString(myEventName);
        setUpEvent(eventDisplayOptions, actorName);

        myEventDisplay = new VBox();
        VBox myActions = new VBox();
        VBox myEventButtons = new VBox();

        myEventDisplay.getChildren().add(myEventParameters);
        myActions.getChildren().add(myEventFactory.createBoxFromResources(ACTION_LISTING));
        myEventButtons.getChildren().add(makeButtons(myEvents,refreshEventOptions));

        myEventDisplay.getChildren().add(myActions);
        myEventDisplay.getChildren().add(myEventButtons);
        myEventDisplay.getStylesheets().add("default.css");

        Scene myScene = new Scene(myEventDisplay);
        this.setScene(myScene);
        this.show();
    }

    private void setUpEvent(String eventDisplayOptions, String actorName) throws UIException {
        eventParameters = new Object[eventDisplayOptions.split(",").length + 1];
        eventParameters[0] = actorName;
        int paramCounter = 1;
        for (String nodeType: eventDisplayOptions.split(",")) {
            String methodName = nodeType.substring(0,nodeType.indexOf("::"));
            String methodParameter = nodeType.substring(nodeType.indexOf("::") + 2);
            try {
                Method m = myEventFactory.getClass().getDeclaredMethod(methodName, String.class);
                Object[] paramMethod = {methodParameter};
                Object addedOption = m.invoke(myEventFactory,paramMethod);
                myEventParameters.getChildren().add((Node)addedOption);
                if (addedOption instanceof ComboBox<?>) {
                    eventParameters[paramCounter] = ((ComboBox<?>)addedOption).getValue().toString();
                }
                if (addedOption instanceof TextField)
                    eventParameters[paramCounter] = ((TextField)addedOption).getText();
                paramCounter++;
            }
            catch (Exception e){
                throw new UIException(INVALID_EVENT_EXCEPTION);
            }

        }
    }

    private Node makeButtons(ObservableList<Event> userMadeEvents, Refresher refreshOptions){
        Button mySaveButton = new Button("Save");
        mySaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateUserEvent(userMadeEvents);
                refreshOptions.refresh();
            }
        });
        Button myCancelButton = new Button("Cancel");
        myCancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                closeThisPane();
            }
        });

        return Utility.createButtonBar(Arrays.asList(mySaveButton,myCancelButton));
    }
    private void closeThisPane(){
        this.close();
    }
    private Object getParameterValue(Node param){
        try {
            Method m = param.getClass().getDeclaredMethod(GET_VALUE_METHOD);
            return m.invoke(param);

        } catch (Exception e) {
            try {
                Method m = param.getClass().getDeclaredMethod(GET_TEXT_METHOD);
                return m.invoke(param);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return "";
    }

    private void generateUserEvent(ObservableList<Event> userMadeEvents){
        Class<?>[] constructorClassReferences = EventType.valueOf(myEventName.toUpperCase()).getConstructorTypes();
        try {
            Class eventClass = EventType.valueOf(myEventName.toUpperCase()).getClassName();
            Constructor eventConstructor = eventClass.getConstructor(constructorClassReferences);
            Event userMadeEvent = (Event) eventConstructor.newInstance(eventParameters);
            userMadeEvents.add(userMadeEvent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }





}
