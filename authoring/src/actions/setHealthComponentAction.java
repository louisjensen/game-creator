package actions;

import engine.external.Entity;
import engine.external.component.HealthComponent;

import java.util.function.Consumer;

public class setHealthComponentAction {

    public setHealthComponentAction(){ }

    public Consumer<Entity> makeHealthComponentAction(double health) {
        return (Consumer<Entity>) (entity) -> {
            HealthComponent component = (HealthComponent) entity.getComponent(HealthComponent.class);
            component.setValue(health);
        };
    }
}
