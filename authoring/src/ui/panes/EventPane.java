package ui.panes;
import events.Event;
import events.EventFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.UIException;
import ui.Utility;

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
    private static final String GET_VALUE_METHOD = "getValue";
    private static final String GET_TEXT_METHOD = "getText";
    private static final String INVALID_EVENT_EXCEPTION = "This event is not accessible to users";

    private String myEventName;
    private String myClassName;
    private EventFactory myEventFactory = new EventFactory();
    private VBox myEventParameters = new VBox();

    public EventPane(String eventDisplayName, ObservableList<Event> myEvents) throws UIException{
        myEventName = eventDisplayName;
        myClassName = ResourceBundle.getBundle(EVENT_CLASS_NAME).getString(eventDisplayName);
        
        String eventDisplayOptions = ResourceBundle.getBundle(EVENT_PARAMETERS).getString(eventDisplayName);
        setUpEvent(eventDisplayOptions);

        VBox myEventButtons = new VBox();
        myEventButtons.getChildren().add(makeButtons(myEvents));
        VBox myEventDisplay = new VBox();
        myEventDisplay.getChildren().add(myEventParameters);
        myEventDisplay.getChildren().add(myEventButtons);
        myEventDisplay.getStylesheets().add("default.css");

        Scene myScene = new Scene(myEventDisplay);
        this.setScene(myScene);
        this.show();
    }

    private void setUpEvent(String eventDisplayOptions) throws UIException {
        for (String nodeType: eventDisplayOptions.split(",")) {
            String methodName = nodeType.substring(0,nodeType.indexOf("::"));
            String methodParameter = nodeType.substring(nodeType.indexOf("::") + 2);
            try {
                Method m = myEventFactory.getClass().getDeclaredMethod(methodName, String.class);
                Object[] paramMethod = {methodParameter};
                Object addedOption = m.invoke(myEventFactory,paramMethod);
                myEventParameters.getChildren().add((Node)addedOption);
            }
            catch (Exception e){
                throw new UIException(INVALID_EVENT_EXCEPTION);
            }

        }
    }

    private Node makeButtons(ObservableList<Event> userMadeEvents){
        Button mySaveButton = new Button("Save");
        mySaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                generateUserEvent(userMadeEvents);
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
        ResourceBundle myConstructorResources = ResourceBundle.getBundle(EVENT_CONSTRUCTORS);
        String[] constructorClassTypes = myConstructorResources.getString(myEventName).split(",");
        Class<?>[] constructorClassReferences = new Class<?>[constructorClassTypes.length];
        Object[] constructorParameters = new Object[constructorClassTypes.length];
        for (int i = 0; i < constructorClassTypes.length; i++){
            constructorClassReferences[i] = constructorClassTypes[i].getClass();
            constructorParameters[i] = getParameterValue(myEventParameters.getChildren().get(i));
        }
        try {
            Class eventClass = Class.forName(myEventName);
            Constructor eventConstructor = eventClass.getConstructor(constructorClassReferences);
            Event userMadeEvent = (Event) eventConstructor.newInstance(constructorParameters);
            userMadeEvents.add(userMadeEvent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }





}
