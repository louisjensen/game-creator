package events;

public enum EventType {
    COLLISION ("Collision", LeftCollisionEvent.class),
    CONDITION ("Conditional", ConditionalEvent.class),
    TIMER ("Timer", TimerEvent.class),
    GENESIS ("On Creation", InstanceCreatedEvent.class);

    private final String displayName;
    private final Class<?> className;
    EventType(String displayName, Class<?> className) {
        this.displayName = displayName;
        this.className = className;
    }


}
