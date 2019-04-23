package ui.manager;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.*;
import ui.panes.CurrentEventsPane;
import ui.panes.EventPane;
import ui.panes.EventsPopUpPane;

/**
 * The EventManager handles displaying options for the user to create a new event according to the particular AuthoringEntity
 * that is currently in focus. These options include adding a new event, removing one previously made, and in the future,
 * modifying current ones
 * @see EventPane
 * @author Harry Ross, Anna Darwish
 */
public class EventManager extends Stage {

    private AuthoringEntity myEntity;
    private String myEntityName;
    private Refresher refreshEventsListing = () -> refreshEventListing();
    private static final String TITLE = "'s Events";
    private static final String ADD_EVENT = "+ Event";
    private static final String STYLE = "default.css";
    public EventManager(Propertable prop) { // Loads common Events for object instance based on type label
        myEntity = (AuthoringEntity) prop; // EventManager is only ever used for an Entity, so cast can happen
        Scene myDefaultScene = createPane();
        this.setScene(myDefaultScene);
        myEntityName = myEntity.getPropertyMap().get(EntityField.LABEL);
    }

    private Scene createPane() {
        BorderPane myEventsDisplay = new BorderPane();
        myEventsDisplay.setTop(createTitle());
        myEventsDisplay.setLeft(null);
        myEventsDisplay.setCenter(new CurrentEventsPane(myEntity.getEvents(), refreshEventsListing));
        myEventsDisplay.setRight(null);
        myEventsDisplay.setBottom(createEventsToolPane());
        Scene myScene = new Scene(myEventsDisplay);
        this.setMinHeight(700);
        this.setMinWidth(800);
        this.setMaxHeight(700);
        this.setMaxWidth(800);
        this.setResizable(false);
        myScene.getStylesheets().add(STYLE);
        return myScene;
    }

    private VBox createTitle(){
        VBox myEntityTile = new VBox();
        myEntityTile.setAlignment(Pos.CENTER);
        myEntityTile.getChildren().add(new Label(myEntityName + TITLE));
        return myEntityTile;
    }

    private VBox createEventsToolPane(){
        VBox myTools = new VBox();
        Button myEventsPopUp = new Button(ADD_EVENT);
        myEventsPopUp.setOnMouseClicked(e -> new EventsPopUpPane(myEntity.getEvents(), myEntityName,refreshEventsListing ));
        myTools.getChildren().add(myEventsPopUp);
        return myTools;
    }


    private void refreshEventListing(){
        this.setScene(createPane());
    }

}
