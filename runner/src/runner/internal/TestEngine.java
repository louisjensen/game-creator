package runner.internal;

import engine.external.Entity;
import engine.external.PositionComponent;
import javafx.geometry.Point3D;

import java.util.List;

public class TestEngine {
    private List<Entity> myEntities;

    public TestEngine(List<Entity> entities){
        myEntities = entities;
    }

    public void update(){
        moveStuffRight();
    }

    private void moveStuffRight(){
        for(Entity entity : myEntities){
            PositionComponent positionComponent = (PositionComponent) entity.getComponent(PositionComponent.class);
            Point3D position = (Point3D) positionComponent.getValue();
            double x = position.getX() + 2;
            double y = position.getY();// + velocityComponent.getValue().getY();
            double z = position.getZ();

            positionComponent.setValue(new Point3D(x, y, z));
        }
    }
}

