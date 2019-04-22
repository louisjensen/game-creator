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

public class EventEditorPane extends Stage {
    private static final String REMOVE= "Remove";
    private static final String REMOVE_CONDITIONS_METHOD_NAME = "removeConditions";
    private static final String ADD_CONDITIONS_METHOD_NAME = "addConditions";
    private static final String REMOVE_ACTIONS_METHOD_NAME = "removeActions";
    private static final String ADD_ACTIONS_METHOD_NAME = "addActions";
    private static final String EVENT_MODIFIER = "event_modifier";

    EventEditorPane(Event unfinishedEvent,Refresher eventDisplayRefresher){
        HBox splitEditorPane = new HBox();

        List<?> myEventConditions = unfinishedEvent.getEventInformation().get(Condition.class);
        List<?> myEventActions = unfinishedEvent.getEventInformation().get(Action.class);

        splitEditorPane.getChildren().add(conditionsPane(myEventConditions,unfinishedEvent, REMOVE_CONDITIONS_METHOD_NAME));
        splitEditorPane.getChildren().add(conditionsPane(myEventActions,unfinishedEvent,REMOVE_ACTIONS_METHOD_NAME));

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
        myButton.setOnMouseClicked(mouseEvent -> {
            Stage myPopUpStage = new Stage();
            VBox myDisplay = new VBox();
            HBox controls = new HBox();
            myDisplay.getChildren().add(controls);
            myPopUpStage.setScene(new Scene(myDisplay));
            Map<String, StringProperty> myConditionStorer = new HashMap<>();
            EventFactory myFact = new EventFactory();
            myFact.factoryDelegator(EVENT_MODIFIER, adderType, controls, myConditionStorer);
        });
    }




}
