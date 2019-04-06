package ui.panes;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.manager.AssetManager;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Carrie Hunner
 * This class creates the pane displaying the default types
 * provided
 */
public class DefaultTypesPane extends VBox{
    private EntityMenu myEntityMenu;
    private ResourceBundle myResources;
    private UserCreatedTypesPane myUserCreatedTypesPane;

    private static final String RESOURCE = "default_entity_type";
    private static final String TITLE_KEY = "DefaultTitle";
    private static final String TABS = "Tabs";
    private static final String TABS_REGEX = ",";

    /**
     * Creates a new pane to display the default types
     */
    public DefaultTypesPane(UserCreatedTypesPane userCreatedTypesPane)     {
        myResources = ResourceBundle.getBundle(RESOURCE);
        myEntityMenu = new EntityMenu(myResources.getString(TITLE_KEY));
        myUserCreatedTypesPane = userCreatedTypesPane;
        populateEntityMenu();
        this.getChildren().add(myEntityMenu);
    }

    private void populateEntityMenu(){
        String[] tabNames = myResources.getString(TABS).split(TABS_REGEX);
        for(String s1 : tabNames){
            ArrayList<Pane> labelsList = new ArrayList<>();
            String[] tabContentKeys = myResources.getString(s1).split(TABS_REGEX);
            for(String s2 : tabContentKeys){
                System.out.println(s2);
                String[] contentDefaultInfo = myResources.getString(s2).split(TABS_REGEX);
                Label label = new Label(contentDefaultInfo[0]);
                Pane pane = new Pane(label);
                pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        CreateNewTypePane createNewTypePane = new CreateNewTypePane(s1, s2);
                        createNewTypePane.show();
                    }
                });
                labelsList.add(pane);
            }
            myEntityMenu.addDropDown(s1);
            myEntityMenu.addToDropDown(s1, labelsList);
        }

    }

}
