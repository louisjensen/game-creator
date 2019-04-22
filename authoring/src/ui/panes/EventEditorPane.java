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
    private static final String EVENT_MODIFIER = "event_modifier";
    private static final String ACTION = "Action";
    private static final String CONDITION = "Condition";
    private Stage myPopUpStage;

    EventEditorPane(Event unfinishedEvent,Refresher eventDisplayRefresher){
        HBox splitEditorPane = new HBox();
        splitEditorPane.setMinSize(600,400);
        List<?> myEventConditions = unfinishedEvent.getEventInformation().get(Condition.class);
        List<?> myEventActions = unfinishedEvent.getEventInformation().get(Action.class);
        ScrollPane myConditionScroll = conditionsPane(myEventConditions,unfinishedEvent, REMOVE_CONDITIONS_METHOD_NAME);
        myConditionScroll.setMaxSize(300,400);
        ScrollPane myActionScroll = conditionsPane(myEventActions,unfinishedEvent,REMOVE_ACTIONS_METHOD_NAME);
        myActionScroll.setMaxSize(300,400);
        splitEditorPane.getChildren().add(myConditionScroll);
        splitEditorPane.getChildren().add(myActionScroll);

        Scene myScene = new Scene(splitEditorPane);
        myScene.getStylesheets().add("default.css");
        this.setOnCloseRequest(windowEvent -> eventDisplayRefresher.refresh());
        this.setScene(myScene);
    }

    private ScrollPane conditionsPane(List<?> myConditions, Event event, String methodName){
        ScrollPane myPane = new ScrollPane();
        VBox myListing = new VBox();
        for (Object eventElement: myConditions){
            VBox eventSubInformation = new VBox();
            Button removeButton = new Button(REMOVE);

            eventSubInformation.getChildren().add(EventFactory.createLabel(eventElement.toString()));
            eventSubInformation.getChildren().add(removeButton);

            myListing.getChildren().add(eventSubInformation);
            setUpRemoveButton(removeButton,eventElement,event,methodName,myListing,eventSubInformation);
        }
        Button addButton = new Button(ADD);
        myListing.getChildren().add(addButton);
        if (methodName.contains(CONDITION)){
            setUpAddButton(addButton,event,myListing,CONDITION);
        }
        else {
            setUpAddButton(addButton,event,myListing,ACTION);
        }
        myPane.setContent(myListing);
        return myPane;
    }



    private void setUpRemoveButton(Button myButton, Object eventElement, Event event, String eventMethod,VBox parent,VBox child){
        myButton.setOnMouseClicked(mouseEvent -> {
            Reflection.callMethod(event,eventMethod,eventElement);
            parent.getChildren().remove(child);
        });
    }

    private void setUpAddButton(Button myButton, Event event,VBox parent,String adderType){
        myButton.setOnMouseClicked(mouseEvent -> displayEventComponentMaker(event,parent,adderType));
    }

    private void displayEventComponentMaker(Event event,VBox parent, String adderType){
        VBox myDisplay = getEventControls(event,parent,adderType);
        myDisplay.getStylesheets().add("default.css");
        myPopUpStage = new Stage();
        myPopUpStage.setScene(new Scene(myDisplay));
        myPopUpStage.show();

    }

    private VBox getEventControls(Event myEvent,VBox parent, String componentType){
        VBox myDisplay = new VBox();
        HBox controls = new HBox();

        Map<String, StringProperty> myStorer = new HashMap<>();
        EventFactory myFactory = new EventFactory();
        myFactory.factoryDelegator(EVENT_MODIFIER, componentType, controls, myStorer);


        myDisplay.getChildren().add(controls);
        if (componentType.equals(CONDITION)) {
            myDisplay.getChildren().add(saveConditionButton(myStorer, myEvent, parent));
        }
        if (componentType.equals(ACTION)){
            myDisplay.getChildren().add(saveActionButton(myStorer,myEvent,parent));
        }
        return myDisplay;
    }

    private Button saveConditionButton(Map<String,StringProperty> myConditionStorer, Event myEvent,VBox parent){
        Button mySaveButton = new Button(SAVE);
        EventBuilder myBuilder = new EventBuilder();
        mySaveButton.setOnMouseClicked(mouseEvent -> {
            String methodName = "createGeneralCondition";
            Condition myCondition = (Condition)Reflection.callMethod(myBuilder,methodName,myConditionStorer);
            parent.getChildren().add(parent.getChildren().size()-1,EventFactory.createLabel(myCondition.toString()));
            myEvent.addConditions(myCondition);
            myPopUpStage.close();
        });
        return mySaveButton;
    }

    private Button saveActionButton(Map<String,StringProperty> myConditionStorer, Event myEvent,VBox parent){
        Button mySaveButton = new Button(SAVE);
        EventBuilder myBuilder = new EventBuilder();
        mySaveButton.setOnMouseClicked(mouseEvent -> {
            String methodName = "createGeneralAction";
            Action myAction = (Action)Reflection.callMethod(myBuilder,methodName,myConditionStorer);
            parent.getChildren().add(parent.getChildren().size()-1,EventFactory.createLabel(myAction.toString()));
            myEvent.addActions(myAction);
            myPopUpStage.close();
        });
        return mySaveButton;
    }




}
