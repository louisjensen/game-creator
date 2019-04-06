package engine.external;

import engine.external.component.*;
import engine.internal.systems.*;

import java.util.Arrays;
import java.util.Collection;

public class Engine {
    //TODO: Read from external source file instead of hardcoding final variables for system initialization
    private final Collection<Class<? extends Component>> MOVEMENT_SYSTEM_COMPONENTS = Arrays.asList(VelocityComponent.class, PositionComponent.class);
    private final Collection<Class<? extends Component>> COLLISION_SYSTEM_COMPONENTS = Arrays.asList(CollisionComponent.class, PositionComponent.class);
    private final Collection<Class<? extends Component>> CONTACT_SYSTEM_COMPONENTS = Arrays.asList(CollisionComponent.class, PositionComponent.class);
    private final Collection<Class<? extends Component>> HEALTH_SYSTEM_COMPONENTS = Arrays.asList(HealthComponent.class);
    private final Collection<Class<? extends Component>> CLEANUP_SYSTEM_COMPONENTS = Arrays.asList(DestroyComponent.class);



    protected Collection<Entity> myEntities;
    private MovementSystem myMovementSystem;
    private CollisionSystem myCollisionSystem;
    private ContactSystem myContactSystem;
    private HealthSystem myHealthSystem;
    private EventHandlerSystem myEventHandlerSystem;
    private CleanupSystem myCleanupSystem;


    public Engine(Level level){
        myEntities = level.getEntities();
        initSystems();
    }

    public void updateState(){
        myMovementSystem.update(myEntities);
        myContactSystem.update(myEntities);
        myCollisionSystem.update(myEntities);
        myHealthSystem.update(myEntities);
        myCleanupSystem.update(myEntities);
        myEventHandlerSystem.update(myEntities);
    }

    public Collection<Entity> getEntities(){
        return myEntities;
    }

    private void initSystems(){
        myMovementSystem = new MovementSystem(MOVEMENT_SYSTEM_COMPONENTS);
        myCollisionSystem = new CollisionSystem(COLLISION_SYSTEM_COMPONENTS);
        myContactSystem = new ContactSystem(CONTACT_SYSTEM_COMPONENTS);
        myHealthSystem = new HealthSystem(HEALTH_SYSTEM_COMPONENTS);
        myCleanupSystem = new CleanupSystem(CLEANUP_SYSTEM_COMPONENTS);
    }
}
