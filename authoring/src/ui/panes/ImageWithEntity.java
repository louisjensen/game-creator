package ui.panes;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.AuthoringEntity;
import ui.EntityField;

import java.io.FileInputStream;

public class ImageWithEntity extends ImageView {
    private AuthoringEntity myAuthoringEntity;

    public ImageWithEntity(FileInputStream s, AuthoringEntity authoringEntity, double width, double height) {
        super(new Image(s, width, height, false, false));
        myAuthoringEntity = authoringEntity;
        myAuthoringEntity.getPropertyMap().addListener((MapChangeListener<Enum, String>) change -> handleChange(change));
    }

    private void handleChange(MapChangeListener.Change<? extends Enum,? extends String> change) {
        if(change.wasAdded()){
            //updateImage(change.getKey(), change.getValueAdded());
            //change.getKey().toString()
        }
    }


    /**
     * Returns AuthoringEntity associated with this object
     * @return AuthoringEntity
     */
    public AuthoringEntity getAuthoringEntity(){
        return myAuthoringEntity;
    }

}
