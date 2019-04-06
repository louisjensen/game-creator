package actions;

import engine.external.Entity;
import engine.external.component.HealthComponent;

import java.util.function.Consumer;

public class setHealthComponentAction extends Action {

    public setHealthComponentAction(){ super();}

    /**
     *
     * @param health the new value to set the entity's health to
     * @return the Consumer<Entity> that sets an entity's health component to health
     */
    public Consumer<Entity> makeHealthComponentAction(double health) {
        return makeValueAction(health, HealthComponent.class);
    }
}
