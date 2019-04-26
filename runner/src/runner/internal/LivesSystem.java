package runner.internal;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.LivesComponent;

import java.util.Collection;

public class LivesSystem extends RunnerSystem{

    public LivesSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner) {
        super(requiredComponents, levelRunner);
    }

    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(LivesComponent.class)){
                displayScore(entity);
                break;
            }
        }
    }

    private void displayScore(Entity entity) {
        //TODO make update HUD
        System.out.println("Lives: " + entity.getComponent(LivesComponent.class).getValue());

    }
}

