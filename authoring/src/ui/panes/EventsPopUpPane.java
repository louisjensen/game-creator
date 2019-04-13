package ui.panes;
import events.EventFactory;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EventsPopUpPane extends Stage {
    private static final List<String> EVENT_CLASSES = Arrays.asList("COLLISION","CONDITIONAL","TIMER", "CONTROL");
    private static final String RESOURCES = "event_classifier";
    private static final String RESOURCES_SYMBOL = "::";
    private EventFactory myEventFactory = new EventFactory();
    private Scene myScene;
    public EventsPopUpPane(ObservableMap<Enum, String> uniqueComponents){
        GridPane eventTypeChooser = setUpEventsChoices();
        myScene = new Scene(eventTypeChooser);
        myScene.getStylesheets().add("default.css");
        this.setScene(myScene);
        this.show();
    }
    private GridPane setUpEventsChoices(){
        GridPane eventTypeChooser = new GridPane();
        eventTypeChooser.setMinSize(300,300);
        eventTypeChooser.setGridLinesVisible(true);
        int counter = 0;
        ResourceBundle myEventResources = ResourceBundle.getBundle(RESOURCES);
        for (String eventClass: EVENT_CLASSES) {
            String[] eventClassInformation = myEventResources.getString(eventClass).split(RESOURCES_SYMBOL);
            Button eventPrompter = new Button(eventClassInformation[0]);
            eventPrompter.setOnMouseClicked(e -> eventPreferences(eventClassInformation[1]));
            eventTypeChooser.add(eventPrompter, counter/2, counter%2);
            counter++;
        }
        return eventTypeChooser;
    }
    private void eventPreferences(String utilityMethodName){

        VBox myVBox = new VBox();
        try {
                Method m = myEventFactory.getClass().getDeclaredMethod(utilityMethodName);
                Object addedOption = m.invoke(myEventFactory);
                myVBox.getChildren().add((Node)addedOption);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        myScene.setRoot(myVBox);
    }

}
