package ui.panes;
import actions.Action;
import conditions.Condition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.Map;

public class CurrentEventDisplay extends VBox {
    public CurrentEventDisplay(Map<Class<?>, List<?>> myMap){
        if (invalidEvent(myMap)){
            return;
        }
        this.setAlignment(Pos.CENTER);
        setUpLabel(myMap);
        setUpEditToolBar();


    }
    private void setUpLabel(Map<Class<?>, List<?>> myMap){
        String labelText = "IF    ";
        for (Condition c: (List<Condition>)myMap.get(Condition.class)){
            labelText += c.toString() + "\n";
        }
        for (Action a: (List<Action>)myMap.get(Action.class)){
            labelText += "\u26AB " + a.toString() + "\n";
        }

        Label myLabel = new Label(labelText);
        myLabel.getStylesheets().clear();
        myLabel.getStylesheets().add("event_options_style.css");
        myLabel.setTextAlignment(TextAlignment.LEFT);
        this.getChildren().add(new Label(labelText));


    }
    private void setUpEditToolBar(){
        Button editButton = new Button("Edit");
        Button removeButton = new Button("Remove");
        HBox buttons = new HBox();
        buttons.getChildren().add(editButton);
        buttons.getChildren().add(removeButton);
        this.getChildren().add(buttons);
    }

    private boolean invalidEvent(Map<Class<?>, List<?>> myMap) {
        return !myMap.containsKey(Condition.class) || !myMap.containsKey(Action.class) ||
                myMap.get(Condition.class).size() == 0 || myMap.get(Action.class).size() == 0;
    }
}
