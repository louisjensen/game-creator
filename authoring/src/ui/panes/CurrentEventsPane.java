package ui.panes;
import events.Event;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CurrentEventsPane extends ScrollPane {
    private static final String CSS_STYLE_SHEET = "default.css";
    public CurrentEventsPane(ObservableList<Event> myEvents){
        VBox myEventsListing = new VBox();
        for (Event event: myEvents) {
            myEventsListing.getChildren().add(new CurrentEventDisplay(event.getEventInformation()));
        }
        this.setContent(myEventsListing);

    }

}
