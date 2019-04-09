package engine.internal.systems;

import engine.external.component.Component;
import engine.external.Engine;

import java.util.Collection;

public class HealthSystem extends ByteMeSystem {
    public HealthSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    protected void run() {
        //TODO: implement this
    }
}
