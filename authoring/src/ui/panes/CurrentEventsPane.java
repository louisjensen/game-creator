package ui.panes;
import engine.external.events.Event;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import ui.manager.Refresher;

public class CurrentEventsPane extends ScrollPane {
    private static final String CSS_STYLE_SHEET = "default.css";
    private ObservableList<Event> myCurrentEvents;

    private Refresher myCurrentEventsRefresher;
    public CurrentEventsPane(ObservableList<Event> myEvents, Refresher eventsRefresher){
        myCurrentEvents = myEvents;
        myCurrentEventsRefresher = eventsRefresher;
        VBox myEventsListing = new VBox();
        myEventsListing.getStylesheets().add(CSS_STYLE_SHEET);
        Editor myEditor = this::editCurrentEvent;
        Editor myRemover = this::removeCurrentEvent;
        for (Event event: myEvents) {
            CurrentEventDisplay currEventDisplay = new CurrentEventDisplay(event.getEventInformation(),event,myRemover,myEditor);
            myEventsListing.getChildren().add(currEventDisplay);
        }
        this.setContent(myEventsListing);
        this.setMinHeight(630);
        this.setMinWidth(800);
        this.setMaxHeight(630);
        this.setMaxWidth(800);

    }

    public void editCurrentEvent(Event unfinishedEvent){
        EventEditorPane myPane = new EventEditorPane(unfinishedEvent,myCurrentEventsRefresher);
        myPane.initModality(Modality.WINDOW_MODAL);
        myPane.show();
    }

    public void removeCurrentEvent(Event obsoleteEvent){
        myCurrentEvents.remove(obsoleteEvent);
        myCurrentEventsRefresher.refresh();
    }

}
