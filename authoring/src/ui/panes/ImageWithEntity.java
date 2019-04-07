package ui.panes;

import engine.external.Entity;
import javafx.scene.image.Image;

import java.io.FileInputStream;

public class ImageWithEntity extends Image {
    private Entity myEntity;
    public ImageWithEntity(FileInputStream s, Entity entity, int width, int height) {
        super(s, width, height, false, false);
        myEntity = entity;
    }

    public Entity getEntity(){
        return myEntity;
    }
}
