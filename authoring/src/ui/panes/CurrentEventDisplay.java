package ui.panes;
import engine.external.actions.Action;
import engine.external.conditions.Condition;
import engine.external.events.Event;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import ui.UIException;

import java.util.*;

    class CurrentEventDisplay extends VBox {
    private Event myEvent;
    private Editor myEventRemover;
    private Editor myEventModifier;
    private ResourceBundle myKeyCodes = ResourceBundle.getBundle("keycode");
    private ResourceBundle myErrorMessage = ResourceBundle.getBundle("error_messages");
    private static final String IF = "IF    ";
    private static final String EDIT = "Edit";
    private static final String REMOVE = "Remove";
    private static final String STYLE = "event_options_style.css";
    private static final String DELIMITER = ".";
    CurrentEventDisplay(Map<Class<?>, List<?>> myMap, Event myEvent, Editor eventRemover, Editor eventModifier){
        this.myEvent = myEvent;
        this.myEventRemover = eventRemover;
        this.myEventModifier = eventModifier;
        if (invalidEvent(myMap)){
            return;
        }
        this.setAlignment(Pos.CENTER);
        setUpLabel(myMap);
        setUpEditToolBar();

    }
    private void setUpLabel(Map<Class<?>, List<?>> myMap){
        StringBuilder labelText = new StringBuilder(IF);
        try {
            for (Condition c : (List<Condition>) myMap.get(Condition.class)) {
                labelText.append(c.toString()).append("\n");
            }
            for (Action a : (List<Action>) myMap.get(Action.class)) {
                labelText.append("\u26AB ").append(a.toString()).append("\n");
            }
        }
        catch(ClassCastException e){
            UIException wrongCast = new UIException(myErrorMessage.getString(this.getClass().getSimpleName()));
            wrongCast.displayUIException();
        }
        Label myLabel = new Label(labelText.toString());
        myLabel.getStylesheets().clear();
        myLabel.getStylesheets().add(STYLE);
        myLabel.setTextAlignment(TextAlignment.LEFT);
        this.getChildren().add(new Label(labelText.toString()));


    }
    private void setUpEditToolBar(){
        Button editButton = new Button(EDIT);
        Button removeButton = new Button(REMOVE);
        ChoiceBox<String> keyCode = new ChoiceBox<>();
        setUpKeyCodes(keyCode);
        removeButton.setOnMouseClicked(mouseEvent -> myEventRemover.editEvent(myEvent));
        editButton.setOnMouseClicked(mouseEvent -> myEventModifier.editEvent(myEvent));

        HBox buttons = new HBox();
        buttons.getChildren().add(editButton);
        buttons.getChildren().add(removeButton);
        buttons.getChildren().add(keyCode);
        this.getChildren().add(buttons);
    }

    private boolean invalidEvent(Map<Class<?>, List<?>> myMap) {
        return !myMap.containsKey(Condition.class) || !myMap.containsKey(Action.class) ||
                myMap.get(Condition.class).size() == 0 || myMap.get(Action.class).size() == 0;
    }

    private void setUpKeyCodes(ChoiceBox<String> myKeyCodesListing){
        Set<String> keyCodes = myKeyCodes.keySet();
        List<String> keyCodesList = new ArrayList<>(keyCodes);
        Collections.sort(keyCodesList);
        List<String> removedIndex = new ArrayList<>();
        for (String key: keyCodesList){
            removedIndex.add(key.substring(key.indexOf(DELIMITER) + 1));
        }
        myKeyCodesListing.setItems(FXCollections.observableList(removedIndex));
        myKeyCodesListing.setOnAction(actionEvent -> {
                myKeyCodesListing.setAccessibleText(myKeyCodesListing.getValue());
                myEvent.clearInputs();
                myEvent.addInputs(KeyCode.getKeyCode(myKeyCodesListing.getValue()));
        });

    }
}
