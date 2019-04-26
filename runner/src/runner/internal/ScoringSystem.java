package runner.internal;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.ScoreComponent;

import java.util.Collection;

public class ScoringSystem extends RunnerSystem{

    public ScoringSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner) {
        super(requiredComponents, levelRunner);
    }

    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(ScoreComponent.class)){
                displayScore(entity);
                break;
            }
        }
    }

    private void displayScore(Entity entity) {
        //TODO make update HUD
        System.out.println("Score: " + entity.getComponent(ScoreComponent.class).getValue());

    }
}
