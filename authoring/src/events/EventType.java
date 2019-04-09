package events;

import java.util.Arrays;
import java.util.List;

public enum EventType {
    COLLISION ("Collision", LeftCollisionEvent.class),
    CONDITION ("Conditional", ConditionalEvent.class),
    TIMER ("Timer", TimerEvent.class),
    GENESIS ("On Creation", InstanceCreatedEvent.class);
    public static final List<String> allDisplayNames = Arrays.asList(COLLISION.displayName,
            CONDITION.displayName,TIMER.displayName, GENESIS.displayName);
    private final String displayName;
    private final Class<?> className;
    EventType(String displayName, Class<?> className) {
        this.displayName = displayName;
        this.className = className;
    }


}
