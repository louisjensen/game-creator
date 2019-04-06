package engine.internal.systems;

<<<<<<< HEAD
import engine.external.component.Component;

import java.util.Collection;

public class EventHandlerSystem extends System {
=======
import engine.external.Event;
import engine.external.component.Component;

import java.util.Collection;
import java.util.List;


/**
 * Checks all Events every cycle
 */
public class EventHandlerSystem extends System {
    List<Event> events;

    //TODO: feed Constructor a list of Events, not Components
>>>>>>> 4a80fdcc8103fd44c7a7d5a9e77eef18b5e3ea3a
    public EventHandlerSystem(Collection<Class<? extends Component>> requiredComponents) {
        super(requiredComponents);
    }

    @Override
    protected void run() {
<<<<<<< HEAD
        //TODO: implement this
=======
        for (Event e : events) {
            e.execute();
        }
>>>>>>> 4a80fdcc8103fd44c7a7d5a9e77eef18b5e3ea3a
    }
}
