package engine.external;

import java.util.Collection;

public interface IEngine {

    Collection<Entity> updateState();

    Collection<Entity> getEntities();

    Collection<Entity> removeEntity(Entity entity);
}
