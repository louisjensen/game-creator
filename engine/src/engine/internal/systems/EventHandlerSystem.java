package engine.internal.systems;


import engine.external.IEvent;
import engine.external.component.Component;
import engine.internal.Engine;

import java.util.Collection;
import java.util.List;


/**
 * Checks all Events every cycle
 */
public class EventHandlerSystem extends System {
    List<IEvent> events;

    //TODO: feed Constructor a list of Events, not Components
    public EventHandlerSystem(Collection<Class<? extends Component>> requiredComponents, Engine engine) {
        super(requiredComponents, engine);
    }

    @Override
    protected void run() {
        //TODO: implement this

        for (IEvent e : events) {
            //e.execute;
        }
    }
}
