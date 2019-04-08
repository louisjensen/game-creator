package actions;

import engine.external.Entity;
import engine.external.component.Component;

import java.util.function.Consumer;

public abstract class Action<T> {
    private Consumer<Entity> myAction;

    protected void setAbsoluteAction(T newValue, Class<? extends Component<T>> componentClass) {
        setAction((entity) -> {
            Component component = entity.getComponent(componentClass);
            component.setValue(newValue);
        });
    }

    protected void setAction(Consumer<Entity> action) {
        myAction = action;
    }

    public Consumer<Entity> getAction() {
        return myAction;
    }
}
