package runner.internal;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.NextLevelComponent;
import engine.external.component.ProgressionComponent;

import java.util.Collection;

public class ProgressionSystem extends RunnerSystem {


    public ProgressionSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner) {
        super(requiredComponents, levelRunner);
    }

    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(ProgressionComponent.class)&&(Boolean) getComponentValue(ProgressionComponent.class,entity)){
                progressIfNecessary(entity);
                break;
            }
        }
    }

    private void progressIfNecessary(Entity entity){
        System.out.println(entity.getComponent(NextLevelComponent.class).getValue());
        System.out.println(entity.getComponent(ProgressionComponent.class).getValue());
        Double nextLevel = (Double) entity.getComponent(NextLevelComponent.class).getValue();
        for(Entity e : myLevelRunner.getEntities()) {
            for (Component<?> component : e.getComponentMap().values()) {
                component.resetToOriginal();
            }
        }
        try {
            myLevelRunner.endLevel(nextLevel);
        } catch (IndexOutOfBoundsException e){
            System.out.println("GAME BEATEN");
        }
    }
}
