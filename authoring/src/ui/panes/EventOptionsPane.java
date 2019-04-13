package ui.panes;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ui.manager.Refresher;

import java.util.*;

public class EventOptionsPane extends VBox {
    private static final String EVENT_CLASSIFIER_RESOURCE = "component_options";
    public EventOptionsPane(List<String> allEntities, List<String> uniqueReactiveEntities,
                            ObservableMap<Enum, String> uniqueComponents, Refresher refreshEventsDisplay){

        this.getChildren().add(makeOptionsListing(allEntities,uniqueReactiveEntities,"Select Entity..."));
        this.getChildren().add(makeStateListing(uniqueReactiveEntities));

        List<String> optionalComponentsList = new ArrayList<>();
        for (Enum en: uniqueComponents.keySet()){
            optionalComponentsList.add(en.name());
        }

        Collections.sort(optionalComponentsList);
        this.getChildren().add(makeOptionsListing(optionalComponentsList,"Select Component..."));
       // this.getChildren().add(makeStateListing(uniqueComponents));
    }
    //Need a current listing of existing entities
    private ComboBox<String> makeOptionsListing(List<String> fullList, List<String> partialList, String prompt){
        fullList.removeAll(partialList);
       return makeOptionsListing(fullList,prompt);
    }
    private ComboBox<String> makeOptionsListing(List<String> myListing, String prompt){
        ObservableList<String> myObservableListing = FXCollections.observableArrayList(myListing);
        ComboBox<String> myChooser = new ComboBox<>(myObservableListing);
        myChooser.setMinWidth(180);
        myChooser.setMaxWidth(180);
        myChooser.setPromptText(prompt);
        return myChooser;

    }
    private VBox makeStateListing(List<String> availableOptions){
        VBox myEventsListing = new VBox();
        for (String option: availableOptions){
           myEventsListing.getChildren().add(new Label(option));
        }
        return myEventsListing;
    }

}
