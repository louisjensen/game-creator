package engine.internal.systems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.ImageViewComponent;
import engine.external.Engine;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Collection;

/**
 * @author Hsingchih Tang
 * Updates the ImageView visualization and positions for Entities that have a SpriteComponent and PositionComponents
 */
public class ImageViewSystem extends VoogaSystem {

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public ImageViewSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine){
        super(requiredComponents, engine);
    }

    @Override
    /**
     * Assign or update the ImageViewComponents for each eligible Entity
     */
    protected void run() {
        for(Entity entity:this.getEntities()){
            entity.addComponent(new ImageViewComponent(generateImageView(entity)));
        }
    }

    private ImageView generateImageView(Entity entity){
        Image image = new Image((String)entity.getComponent(SPRITE_COMPONENT_CLASS).getValue());
        Double XPosition = (Double)entity.getComponent(X_POSITION_COMPONENT_CLASS).getValue();
        Double YPosition = (Double)entity.getComponent(Y_POSITION_COMPONENT_CLASS).getValue();
        Double width = (Double)entity.getComponent(WIDTH_COMPONENT_CLASS).getValue();
        Double height = (Double)entity.getComponent(HEIGHT_COMPONENT_CLASS).getValue();

        ImageView imageView = new ImageView(image);
        imageView.setX(XPosition);
        imageView.setY(YPosition);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

}
