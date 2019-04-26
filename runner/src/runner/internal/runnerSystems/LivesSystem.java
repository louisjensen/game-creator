package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.LivesComponent;
import runner.internal.HeadsUpDisplay;
import runner.internal.LevelRunner;
import java.util.Collection;

public class LivesSystem extends RunnerSystem {
    private HeadsUpDisplay myHUD;

    public LivesSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner, HeadsUpDisplay hud) {
        super(requiredComponents, levelRunner);
        myHUD = hud;
    }

    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(LivesComponent.class)){
                displayLives(entity);
                break;
            }
        }
    }

    private void displayLives(Entity entity) {
        //TODO make update HUD
        System.out.println("Lives: " + entity.getComponent(LivesComponent.class).getValue());
        myHUD.updateLives((Double) entity.getComponent(LivesComponent.class).getValue());

    }
}

