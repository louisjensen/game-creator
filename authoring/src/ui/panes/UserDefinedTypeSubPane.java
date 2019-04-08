package ui.panes;

import engine.external.Entity;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * @author Carrie Hunner
 * A Subpane that has an imageview on the left and text on the right
 * It also holds an entity and can give that entity
 */
public class UserDefinedTypeSubPane extends GridPane {
    private Entity myEntity;
    private static final int IMAGE_SIZE = 50;
    private static final int GAP = 10;

    /**
     * @param imageView Imageview to be displayed
     * @param title Text to be displayed
     * @param entity Entity to be stored
     */
    public UserDefinedTypeSubPane(ImageView imageView, String title, Entity entity){
        myEntity = entity;
        Label label = new Label(title);
        imageView.setFitHeight(IMAGE_SIZE);
        imageView.setFitWidth(IMAGE_SIZE);
        this.add(imageView, 0, 0);
        this.add(label, 1, 0);
        this.setHgap(GAP);
        this.setVgap(GAP);
    }

    /**
     * Gets the entity stored by this pane
     * @return Entity
     */
    public Entity getEntity(){
        return myEntity;
    }


}
