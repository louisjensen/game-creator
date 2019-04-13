package events;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.panes.EventPane;

import java.util.ResourceBundle;
import java.util.Set;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * This is essentially a Utilities class that EventPane uses in order to display particular options and properties associated
 * with events and actions. This helps with reflection in creating events, as different events need to provide different
 * controls to the user to input information necessary to instantiate different events and actions
 * @see EventPane
 * @author Anna Darwish
 */
public class EventFactory {
    private static final String DEFAULT_RESOURCES_NAME = "basic_event_display";
    private static final String ACTION_RESOURCES_NAME = "actions_display";
    private static final ResourceBundle DEFAULT_RESOURCES = ResourceBundle.getBundle(DEFAULT_RESOURCES_NAME);
    private static final ResourceBundle ACTION_RESOURCES = ResourceBundle.getBundle(ACTION_RESOURCES_NAME);

    public static VBox createCollisionOptions(){
        VBox collisionOptions = new VBox();
//        collisionOptions.getChildren().add(new Label("Collision Preferences"));
//
//        HBox labeledPane = new HBox();
//        labeledPane.getChildren().add(new Label("Direction: "));
//        labeledPane.getChildren().add(createBoxFromResourcesKey("Direction"));
//        collisionOptions.getChildren().add(labeledPane);
//
//        HBox entityPane = new HBox();
//        entityPane.getChildren().add(new Label("Collidee: "));
//        entityPane.getChildren().add(createChoiceBox(Arrays.asList("object1","object2","object3")));
//        collisionOptions.getChildren().add(entityPane);
//
//        HBox actionOptions = createActionsOptions();
//        collisionOptions.getChildren().add(myDirections);
//        collisionOptions.getChildren().add(myEntities);
//        collisionOptions.getChildren().add(actionOptions);
        return collisionOptions;
    }
    public static ChoiceBox<String> createBoxFromResources(String resourcesBundleName, ArrayList<StringProperty> myBinding){
        ResourceBundle optionsResource = ResourceBundle.getBundle(resourcesBundleName);
        Set<String> myActionsSet = optionsResource.keySet();
        List<String> myActionsList = new ArrayList<>(myActionsSet);
        Collections.sort(myActionsList);
        return createChoiceBox(myActionsList,myBinding);

    }

    public static ChoiceBox<String> createBoxFromResourcesKey(String key, ArrayList<StringProperty> myBinding ){
        String[] keyValues = DEFAULT_RESOURCES.getString(key).split("::");
        List<String> myKeyValues = Arrays.asList(keyValues);
        Collections.sort(myKeyValues);
        return createChoiceBox(myKeyValues,myBinding);
    }

    public static ChoiceBox<String> createChoiceBox(List<String> choiceBoxOptions, ArrayList<StringProperty> myBinding){
        
        ObservableList<String> myObservableChoices = FXCollections.observableArrayList(choiceBoxOptions);
        ChoiceBox<String> myChoices = new ChoiceBox<>(myObservableChoices);
        myChoices.getStylesheets().add("default.css");
        myChoices.setOnAction(e -> myChoices.setAccessibleText(myChoices.getValue()));
        StringProperty myListener = new SimpleStringProperty();
        myListener.bindBidirectional(myChoices.accessibleTextProperty());
        myBinding.add(myListener);
        return myChoices;
    }
    public static TextField createDisappearingLabel(String textFieldInformation, ArrayList<StringProperty> myBinding){
        TextField myTextField = new TextField();
        myTextField.setPromptText(textFieldInformation);
        myTextField.setFocusTraversable(false);
        myTextField.setOnMouseClicked(mouseEvent -> myTextField.setText(""));
        StringProperty myListener = new SimpleStringProperty();
        myListener.bindBidirectional(myTextField.textProperty());
        myBinding.add(myListener);
        return myTextField;
        }


    public static HBox createActionsOptions(ArrayList<StringProperty> myActionsBinding){
        HBox myActionOptions = new HBox();
        ChoiceBox<String> modifyChoices = createBoxFromResourcesKey("Modifiers", myActionsBinding);
//        modifyChoices.setPromptText("Modifiers");
        myActionOptions.getChildren().add(modifyChoices);

        List<String> componentOptions = new ArrayList<>(ACTION_RESOURCES.keySet());
        Collections.sort(componentOptions);
        ChoiceBox<String> actionChoices = createChoiceBox(componentOptions,myActionsBinding);
//        actionChoices.setPromptText("Actions");
        myActionOptions.getChildren().add(actionChoices);
        myActionOptions.getChildren().add(createNumericOptions("Value",myActionsBinding));

        return myActionOptions;
    }



    public static TextField createNumericOptions(String numericFieldInformation, ArrayList<StringProperty> myBinding){
        TextField myTextField = createDisappearingLabel(numericFieldInformation,myBinding);
        myTextField.setPromptText(numericFieldInformation);

        myTextField.textProperty().addListener(new ChangeListener<String>() {
            //This stops the user from entering any non-numeric value
            //@Todo Set a default value to help with error checking and guarantee that we will get a valid value from these fields
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    myTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        return myTextField;
    }

}
