package engine.internal.systems;

import engine.external.component.Component;
import engine.external.Engine;

import java.util.Collection;

public class CleanupSystem extends System {
    public CleanupSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    protected void run() {

        //TODO: implement this
    }
}
