package actions;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.ValueComponent;

import java.util.function.Consumer;

public class setValueAction {

    public setValueAction(){ }

    public Consumer<Entity> makeValueAction(double newValue) {
        return (Consumer<Entity>) (entity) -> {
            ValueComponent component = (ValueComponent) entity.getComponent(ValueComponent.class);
            component.setValue(newValue);
        };
    }

}
