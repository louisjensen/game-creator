package ui.panes;

import engine.external.actions.Action;
import engine.external.conditions.Condition;
import engine.external.events.Event;
import events.EventBuilder;
import events.EventFactory;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.manager.Refresher;
import voogasalad.util.reflection.Reflection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

    class EventEditorPane extends Stage {
    private static final String ADD = "Add";
    private static final String REMOVE= "Remove";
    private static final String SAVE = "Save";
    private static final String REMOVE_CONDITIONS_METHOD_NAME = "removeConditions";
    private static final String REMOVE_ACTIONS_METHOD_NAME = "removeActions";
    private static final String ADD_CONDITIONS_METHOD_NAME = "createGeneralCondition";
    private static final String ADD_ACTIONS_METHOD_NAME = "createGeneralAction";

    private static final String EVENT_MODIFIER = "event_modifier";

    private static final String ACTION = "Action";
    private static final String CONDITION = "Condition";

    private static final String STYLE = "default.css";
    private Stage myPopUpStage;

    EventEditorPane(Event unfinishedEvent,Refresher eventDisplayRefresher){
        HBox splitEditorPane = new HBox();

        List<?> myEventConditions = unfinishedEvent.getEventInformation().get(Condition.class);
        List<?> myEventActions = unfinishedEvent.getEventInformation().get(Action.class);

        ScrollPane myConditionScroll = conditionsPane(myEventConditions,unfinishedEvent, REMOVE_CONDITIONS_METHOD_NAME,CONDITION,ADD_CONDITIONS_METHOD_NAME);
        ScrollPane myActionScroll = conditionsPane(myEventActions,unfinishedEvent,REMOVE_ACTIONS_METHOD_NAME,ACTION,ADD_ACTIONS_METHOD_NAME);
        splitEditorPane.getChildren().add(myConditionScroll);
        splitEditorPane.getChildren().add(myActionScroll);

        splitEditorPane.setMinSize(600,400);
        myConditionScroll.setMaxSize(300,400);
        myActionScroll.setMaxSize(300,400);

        Scene myScene = new Scene(splitEditorPane);
        myScene.getStylesheets().add(STYLE);
        this.setOnCloseRequest(windowEvent -> eventDisplayRefresher.refresh());
        this.setScene(myScene);
    }

    private ScrollPane conditionsPane(List<?> myConditions, Event event, String removeMethodName, String addFactoryResources, String addMethodName){
        ScrollPane myPane = new ScrollPane();
        VBox myListing = new VBox();
        for (Object eventElement: myConditions){
            VBox eventSubInformation = new VBox();
            Button removeButton = new Button(REMOVE);

            eventSubInformation.getChildren().add(EventFactory.createLabel(eventElement.toString()));
            eventSubInformation.getChildren().add(removeButton);

            myListing.getChildren().add(eventSubInformation);
            setUpRemoveButton(removeButton,eventElement,event,removeMethodName,myListing,eventSubInformation);
        }
        Button addButton = new Button(ADD);
        myListing.getChildren().add(addButton);
        setUpAddButton(addButton,event,myListing,addFactoryResources,addMethodName);
        myPane.setContent(myListing);
        return myPane;
    }



    private void setUpRemoveButton(Button myButton, Object eventElement, Event event, String eventMethod,VBox parent,VBox child){
        myButton.setOnMouseClicked(mouseEvent -> {
            Reflection.callMethod(event,eventMethod,eventElement);
            parent.getChildren().remove(child);
        });
    }

    private void setUpAddButton(Button myButton, Event event,VBox parent,String factoryResources, String addMethod){
        myButton.setOnMouseClicked(mouseEvent -> displayEventComponentMaker(event,parent,factoryResources,addMethod));
    }

    private void displayEventComponentMaker(Event event,VBox parent, String factoryResources, String addMethod){
        VBox myDisplay = getEventControls(event,parent,factoryResources,addMethod);
        myDisplay.getStylesheets().add("default.css");
        myPopUpStage = new Stage();
        myPopUpStage.setScene(new Scene(myDisplay));
        myPopUpStage.show();

    }

    private VBox getEventControls(Event myEvent,VBox parent, String factoryResources, String addMethod){
        VBox myDisplay = new VBox();
        HBox controls = new HBox();

        Map<String, StringProperty> myStorer = new HashMap<>();
        EventFactory myFactory = new EventFactory();
        myFactory.factoryDelegator(EVENT_MODIFIER, factoryResources, controls, myStorer);


        myDisplay.getChildren().add(controls);
        myDisplay.getChildren().add(saveButton(myStorer, myEvent, parent,addMethod));
        return myDisplay;
    }

    private Button saveButton(Map<String,StringProperty> myConditionStorer,Event myEvent, VBox parent,String methodName){
        Button mySaveButton = new Button(SAVE);
        EventBuilder myBuilder = new EventBuilder();
        mySaveButton.setOnMouseClicked(mouseEvent -> {
            Object myEventComponent = Reflection.callMethod(myBuilder,methodName,myConditionStorer,myEvent);
            parent.getChildren().add(parent.getChildren().size()-1,EventFactory.createLabel(myEventComponent.toString()));
            myPopUpStage.close();
        });
        return mySaveButton;
    }

}



