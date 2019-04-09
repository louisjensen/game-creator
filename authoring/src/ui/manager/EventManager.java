package ui.manager;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ui.EntityField;
import ui.Propertable;
import ui.AuthoringEntity;
import ui.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Harry Ross
 */
public class EventManager extends Stage {

    private AuthoringEntity myEntity;

    public EventManager(Propertable prop) { // Loads common Events for object instance based on type label
        myEntity = (AuthoringEntity) prop; // EventManager is only ever used for an Entity, so cast can happen
        this.setScene(createPane());
        this.setResizable(false);
        createContent();
    }

    private Node createContent() {
        GridPane eventsGrid = new GridPane();

        for (int i = 0; i < myEntity.getEvents().size(); i++) {
            eventsGrid.add(new HBox(new Label(myEntity.getEvents().get(i).toString())), 0, i);
        }

        return new ScrollPane(eventsGrid);
    }

    private Scene createPane() {
        Map<String, List<String>> description = new LinkedHashMap<>();
        description.put("label", new ArrayList<>(Collections.singletonList("Manage " + myEntity.getPropertyMap().get(EntityField.LABEL) + " Events")));
        description.put("sub-label", new ArrayList<>(Collections.singletonList("Add or Remove Events for " + myEntity.getPropertyMap().get(EntityField.LABEL))));

        Button addButton = Utility.makeButton(this, "addEvent", "Add");
        Button removeButton = Utility.makeButton(this, "removeEvent", "Remove");
        Button closeButton = Utility.makeButton(this, "closeWindow", "Close");

        return Utility.createDialogPane(Utility.createLabelsGroup(description), createContent(), Arrays.asList(addButton, removeButton, closeButton));
    }

    private void addEvent() {
        // Open Add dialog for user to enter info, stores result in ObservableList (myEntity.getEvents())
        // Pretty sure I'm gonna replace the gridpane with a listview to make this stuff easier
    }

    private void removeEvent() {
        // Remove selected Event from ObservableList (myEntity.getEvents())
        // We have to allow for the user to select a gridpane cell to remove, maybe replace entirely with listview to make that easier??
    }

    public void closeWindow() {
        this.close();
    }
}
