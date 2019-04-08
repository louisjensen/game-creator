package ui.panes;

import engine.external.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class ImageWithEntity extends ImageView {
    private Entity myEntity;

    public ImageWithEntity(FileInputStream s, Entity entity, double width, double height) {
        super(new Image(s, width, height, false, false));
        myEntity = entity;
    }

    /**
     * Returns entity associated with this object
     * @return Entity
     */
    public Entity getEntity(){
        return myEntity;
    }

}
