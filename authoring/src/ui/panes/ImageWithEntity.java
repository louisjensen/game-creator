package ui.panes;

import engine.external.Entity;
import javafx.scene.image.Image;

import java.io.FileInputStream;

public class ImageWithEntity extends Image {
    private Entity myEntity;
    public ImageWithEntity(FileInputStream s, Entity entity) {
        super(s);
        myEntity = entity;
    }

    public Entity getEntity(){
        return myEntity;
    }
}
