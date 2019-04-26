package runner.internal.runnerSystems;

import engine.external.Entity;
import engine.external.component.Component;
import engine.external.component.ImageViewComponent;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import runner.internal.LevelRunner;
import runner.internal.runnerSystems.RunnerSystem;

import java.util.Collection;

public class ImageDisplaySystem extends RunnerSystem {
    private Group myGroup;

    public ImageDisplaySystem (Collection<Class<? extends Component>> requiredComponents, LevelRunner levelRunner, Group group) {
        super(requiredComponents, levelRunner);
        myGroup = group;
    }

    @Override
    public void run() {
        for(Entity entity:this.getEntities()){
            if(entity.hasComponents(ImageViewComponent.class)){
                displayImage(entity);
            }
        }
    }

    private void displayImage(Entity entity) {
        ImageView image = (ImageView) entity.getComponent(ImageViewComponent.class).getValue();
        myGroup.getChildren().add(image);
    }
}
