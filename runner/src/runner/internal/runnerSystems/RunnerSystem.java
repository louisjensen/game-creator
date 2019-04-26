package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import runner.internal.LevelRunner;

import java.util.ArrayList;
import java.util.Collection;

public abstract class RunnerSystem {

    private Collection<Class<? extends Component>> myRequiredComponents;
    private Collection<Entity> myEntities;
    protected LevelRunner myLevelRunner;

    public RunnerSystem(Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner) {
        myRequiredComponents = requiredComponents;
        myLevelRunner = levelRunner;
    }

    public void update(Collection<Entity> entities) {
        myEntities = new ArrayList<>();

        for (Entity e: entities) {
            if (filter(e)) {
                myEntities.add(e);
            }
        }
        run();
    }

    public boolean filter(Entity entity){
        return entity.hasComponents(myRequiredComponents);
    }

    public abstract void run();

    public Collection<Entity> getEntities() {
        return myEntities;
    }

    public Object getComponentValue(Class<? extends Component> componentClazz,Entity entity){
        return entity.getComponent(componentClazz).getValue();
    }

}
