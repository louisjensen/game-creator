package ui.panes;

import engine.external.Entity;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.DefaultTypeXMLReaderFactory;
import ui.windows.CreateNewTypeWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Carrie Hunner
 * This class creates the page displaying the default types
 * provided
 */
public class DefaultTypesPane extends VBox{
    private EntityMenu myEntityMenu;
    private ResourceBundle myResources;
    private UserCreatedTypesPane myUserCreatedTypesPane;
    private DefaultTypeXMLReaderFactory myDefaultTypesFactory;

    private static final String RESOURCE = "default_entity_type";
    private static final String TITLE_KEY = "DefaultTitle";

    /**
     * Creates a new page to display the default types
     */
    public DefaultTypesPane(UserCreatedTypesPane userCreatedTypesPane)     {
        myResources = ResourceBundle.getBundle(RESOURCE);
        myEntityMenu = new EntityMenu(myResources.getString(TITLE_KEY));
        myUserCreatedTypesPane = userCreatedTypesPane;
        myDefaultTypesFactory = new DefaultTypeXMLReaderFactory();
        populateEntityMenu();
        this.getChildren().add(myEntityMenu);
    }

    private void populateEntityMenu(){
        List<String> tabNames = myDefaultTypesFactory.getCategories();
        for(String category : tabNames){
            ArrayList<Pane> labelsList = new ArrayList<>();
            List<String> specificTypes = myDefaultTypesFactory.getDefaultNames(category);
            for(String name : specificTypes){
                Label label = new Label(name);
                VBox pane = createAndFormatVBox(name, label);
                labelsList.add(pane);
            }
            myEntityMenu.addDropDown(category);
            myEntityMenu.addToDropDown(category, labelsList);
        }

    }

    //takes in the Strings for the type and basedOn as well as the label
    //sets up the event for when clicked on
    private VBox createAndFormatVBox(String defaultName, Label label) {
        VBox pane = new VBox(label);
        pane.setFillWidth(true);
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CreateNewTypeWindow createNewTypeWindow = new CreateNewTypeWindow(defaultName);
                createNewTypeWindow.showAndWait();
                Entity entity = createNewTypeWindow.getUserCreatedEntity();
                String defaultTypeName = createNewTypeWindow.getDefaultTypeName();
                String category = myDefaultTypesFactory.getCategory(defaultName);
                if(entity != null){
                    myUserCreatedTypesPane.addUserDefinedType(entity, defaultName);
                }
            }
        });
        return pane;
    }

}
