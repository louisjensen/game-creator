package runner.internal;

import engine.external.Entity;
import engine.external.Level;
import engine.external.component.PositionComponent;
import javafx.geometry.Point3D;
import javafx.scene.input.KeyCode;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class TestEngine {
    private Collection<Entity> myEntities;

    public TestEngine(Level level){
        myEntities = level.getEntities();
    }

    public Collection<Entity> updateState(Collection<KeyCode> keys){
        moveStuffRight();
        return myEntities;
    }

    private void moveStuffRight(){
        for(Entity entity : myEntities){
            PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
            Point3D position = (Point3D) positionComponent.getValue();
            double x = position.getX() + 1;
            double y = position.getY();// + velocityComponent.getValue().getY();
            double z = position.getZ();

            positionComponent.setValue(new Point3D(x, y, z));
        }
    }

    private void moveRandom(){
        int move = 1;
        for(Entity entity : myEntities){
            PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
            Point3D position = (Point3D) positionComponent.getValue();
            double x = position.getX() + move;
            double y = position.getY() + move;// + velocityComponent.getValue().getY();
            double z = position.getZ();

            positionComponent.setValue(new Point3D(x, y, z));
            move*=-1;
        }
    }
}

