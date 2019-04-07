package ui.panes;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.NameComponent;
import engine.external.component.SpriteComponent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.DefaultTypesFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private static final String ASSET_IMAGE_FOLDER_PATH = "authoring/Assets/Images";

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
        String label = (String) entity.getComponent(new NameComponent("").getClass()).getValue();
        String imageName = (String) entity.getComponent(new SpriteComponent("").getClass()).getValue();
        System.out.println(imageName);
        try {
            ImageWithEntity imageWithEntity = new ImageWithEntity(new FileInputStream(ASSET_IMAGE_FOLDER_PATH + "/" + imageName), new Entity());
            ImageView imageView = new ImageView(imageWithEntity);
            UserDefinedTypeSubPane subPane = new UserDefinedTypeSubPane(imageView, label, entity);
            List<Pane> paneList = new ArrayList<>();
            paneList.add(subPane);
            myEntityMenu.addToDropDown(category, paneList);
        } catch (FileNotFoundException e) {
            //TODO: deal with this
            e.printStackTrace();
        }



    }
}
