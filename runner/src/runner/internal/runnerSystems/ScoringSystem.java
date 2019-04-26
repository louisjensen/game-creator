package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.ScoreComponent;
import runner.internal.HeadsUpDisplay;
import runner.internal.LevelRunner;
import java.util.Collection;

public class ScoringSystem extends RunnerSystem {
    private HeadsUpDisplay myHUD;

    public ScoringSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner, HeadsUpDisplay hud) {
        super(requiredComponents, levelRunner);
        myHUD = hud;
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
        myHUD.updateScore((Double) entity.getComponent(ScoreComponent.class).getValue());
    }
}
