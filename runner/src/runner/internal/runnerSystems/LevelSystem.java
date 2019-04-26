package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.NextLevelComponent;
import runner.internal.HeadsUpDisplay;
import runner.internal.LevelRunner;
import java.util.Collection;

public class LevelSystem extends RunnerSystem {
    private HeadsUpDisplay myHUD;

    public LevelSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner, HeadsUpDisplay hud) {
        super(requiredComponents, levelRunner);
        myHUD = hud;
    }

    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(NextLevelComponent.class)){
                displayLevel(entity);
                break;
            }
        }
    }

    private void displayLevel(Entity entity) {
        //TODO make update HUD
        System.out.println("Level: " + entity.getComponent(NextLevelComponent.class).getValue());
        myHUD.updateLevel((Double) entity.getComponent(NextLevelComponent.class).getValue());
    }
}
