package ui.panes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class EventPane extends Scene {
    private VBox myEventParameters = new VBox();
    private Button myOkButton = new Button("Ok");
    private List<String> myValues = new ArrayList<String>();
    public EventPane(String eventName) {
        super(new VBox());
//        try {
////            Constructor<?> myConstructor = Class.forName(eventName).getConstructor();
////        } catch (Exception e) {
////            System.out.println("No such event exists");
////        }

        myEventParameters.getChildren().add(new TextField("Entity Name"));
        myEventParameters.getChildren().add(new TextField("Entity One Response: "));
        myEventParameters.getChildren().add(new TextField("Entity Two Response: "));
        myEventParameters.getChildren().add(myOkButton);
        setUpButton();
        myEventParameters.getStylesheets().add("default.css");
        this.setRoot(myEventParameters);
    }
    private void setUpButton(){
        myOkButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event2)
            {
                
            }
        });
    }
    public List<String> getMyValues(){
        return myValues;
    }



}
