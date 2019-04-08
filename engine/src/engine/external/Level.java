package engine.external;

import java.util.Collection;
import java.util.HashSet;

public class Level {
    private Collection<Entity> myEntities;
    private Collection<IEventEngine> myEvents;
    private Level myCheckPoint;

    public Level(){
        myEntities = new HashSet<Entity>();
    }

    public void addEntity(Entity entity){
        myEntities.add(entity);
    }

    public Collection<Entity> getEntities(){
        return myEntities;
    }

    public Collection<IEventEngine> getEvents(){
        return myEvents;
    }

}
