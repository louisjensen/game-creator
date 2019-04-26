package runner.internal;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.NextLevelComponent;
import java.util.Collection;

public class LevelSystem extends RunnerSystem{

    public LevelSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner) {
        super(requiredComponents, levelRunner);
    }

    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(NextLevelComponent.class)){
                displayScore(entity);
                break;
            }
        }
    }

    private void displayScore(Entity entity) {
        //TODO make update HUD
        System.out.println("Level: " + entity.getComponent(NextLevelComponent.class).getValue());

    }
}
