package engine.external;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private Map<Class<?>, Component<?>> myComponents;

    public Entity() {
        myComponents = new HashMap<>();
    }

    public void addComponent(Component<?> component) {
        myComponents.put(component.getClass(), component);
    }

    public void removeComponent(Component<?> component){
        myComponents.remove(component.getClass());
    }

}
