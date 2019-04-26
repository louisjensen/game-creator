package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.NextLevelComponent;
import engine.external.component.ProgressionComponent;
import javafx.animation.Animation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import runner.internal.LevelRunner;

import java.util.Collection;
import java.util.function.Consumer;

public class ProgressionSystem extends RunnerSystem {
    private Group myGroup;
    private Stage myStage;
    private Animation myAnimation;
    private int myWidth;
    private int myHeight;
    private Consumer myLevelChanger;

    public ProgressionSystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner,
                              Group group, Stage stage, Animation animation, int width, int height, Consumer consumer) {
        super(requiredComponents, levelRunner);
        myGroup = group;
        myStage = stage;
        myAnimation = animation;
        myWidth = width;
        myHeight = height;
        myLevelChanger = consumer;
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
            endLevel(nextLevel);
        } catch (IndexOutOfBoundsException e){
            System.out.println("GAME BEATEN");
        }
    }

    private void endLevel(Double levelToProgressTo) {
        myGroup.getChildren().clear();
        myAnimation.stop();
        myStage.setScene(new Scene(new Group(), myWidth, myHeight));
        myLevelChanger.accept(levelToProgressTo);
    }
}
