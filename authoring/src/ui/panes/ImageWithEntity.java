package ui.panes;

import engine.external.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;

public class ImageWithEntity extends Image {
    private Entity myEntity;
    private ImageView myImageView;

    public ImageWithEntity(FileInputStream s, Entity entity, int width, int height) {
        super(s, width, height, false, false);
        myEntity = entity;
        myImageView = new ImageView(this);
    }

    /**
     * Returns entity associated with this object
     * @return Entity
     */
    public Entity getEntity(){
        return myEntity;
    }

    /**
     * Returns ImageView associated with this object
     * @return ImageView
     */
    public ImageView getImageView(){
        return myImageView;
    }
}
