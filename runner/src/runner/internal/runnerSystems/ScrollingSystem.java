package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import runner.internal.LevelRunner;
import runner.internal.runnerSystems.RunnerSystem;

import java.util.Collection;

public class ScrollingSystem extends RunnerSystem {
    private Group myGroup;
    private Scene myScene;

    public ScrollingSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner, Group group, Scene scene) {
        super(requiredComponents, levelRunner);
        myGroup = group;
        myScene = scene;
    }

    @Override
    public void run(){
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(CameraComponent.class)&&(Boolean) getComponentValue(CameraComponent.class,entity)) {
                scrollOnMainCharacter(entity);
                break;
            }
        }
    }

    private void scrollOnMainCharacter(Entity entity){
        Double x = (Double) entity.getComponent(XPositionComponent.class).getValue();
        Double origin = myGroup.getTranslateX();
        Double xMinBoundary = myScene.getWidth()/5.0;
        Double xMaxBoundary = myScene.getWidth()/4.0*3;
        if (x < xMinBoundary - origin) {
            myGroup.setTranslateX(-1 * x + xMinBoundary);
        }
        if (x > xMaxBoundary - origin) {
            myGroup.setTranslateX(-1 * x + xMaxBoundary);
        }
    }
}
