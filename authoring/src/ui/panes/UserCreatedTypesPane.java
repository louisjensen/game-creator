package ui.panes;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.NameComponent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.DefaultTypesFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Carrie Hunner
 * This is a pane that will display all of the types created by the user
 */
public class UserCreatedTypesPane extends VBox {
    private EntityMenu myEntityMenu;
    private ResourceBundle myResources;
    private DefaultTypesFactory myDefaultTypesFactory;
    private static final String RESOURCE = "default_entity_type";

    public UserCreatedTypesPane(){
        myResources = ResourceBundle.getBundle(RESOURCE);
        String title = myResources.getString("UserCreatedTitle");
        myEntityMenu = new EntityMenu(title);
        myDefaultTypesFactory = new DefaultTypesFactory();
        populateCategories();
        this.getChildren().add(myEntityMenu);
    }

    private void populateCategories() {
        for(String s : myDefaultTypesFactory.getCategories()){
            myEntityMenu.addDropDown(s);
        }
    }

    public void addUserDefinedType(String category, Entity entity){
        Pane pane = new Pane();
        Label label = new Label();
        Component nameComponent = entity.getComponent(new NameComponent("").getClass());
        label.setText((String) nameComponent.getValue());
        pane.getChildren().add(label);
        List<Pane> paneList = new ArrayList<>();
        paneList.add(pane);
        myEntityMenu.addToDropDown(category, paneList);
    }
}
