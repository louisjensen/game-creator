package engine.internal.systems;

import engine.external.Engine;
import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.DestroyComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * @author Hsingchih Tang
 * Cleans up all unserializable Components that have been added to Entities by Engine
 * before Runner saves the Entities for a running game
 */
public class SaveGameSystem extends VoogaSystem {

    private ResourceBundle REMOVABLE_COMPONENTS = ResourceBundle.getBundle("SystemRemoveComponents");

    /**
     * Accepts a reference to the Engine in charge of all Systems in current game, and a Collection of Component classes
     * that this System would require from an Entity in order to interact with its relevant Components
     * @param requiredComponents collection of Component classes required for an Entity to be processed by this System
     * @param engine the main Engine which initializes all Systems for a game and makes update() calls on each game loop
     */
    public SaveGameSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    public void run(){
        for(Entity entity : this.getEntities()){
            ArrayList<Class<? extends Component>> components = new ArrayList<>();
            if(entity.hasComponents(IMAGEVIEW_COMPONENT_CLASS)){
                components.add(IMAGEVIEW_COMPONENT_CLASS);
            }
        }
    }
}
