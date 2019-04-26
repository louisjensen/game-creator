package engine.external.actions;

import engine.external.Entity;
import engine.external.component.Component;

import java.io.Serializable;
import java.util.function.Consumer;

public abstract class AddComponentAction<T> extends Action<T>{

    protected void setAbsoluteAction(Component component) {
        setAction((Consumer<Entity> & Serializable) (entity) -> {
            entity.addComponent(component);
        });
    }
}
