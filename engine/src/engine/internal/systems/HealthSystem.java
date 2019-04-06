package engine.internal.systems;

import engine.external.component.Component;

import java.util.Collection;

public class HealthSystem extends System{
    public HealthSystem(Collection<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
    }

    @Override
    protected void run() {
        //TODO: implement this
    }
}
