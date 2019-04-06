package engine.external;

import engine.external.component.NameComponent;
import engine.external.component.PositionComponent;
import javafx.geometry.Point3D;

public class ModuleTester {
    public static void main(String[] args){
        Entity myEntity = new Entity();
        myEntity.addComponent(new NameComponent("Doovall"));
        myEntity.addComponent(new PositionComponent(new Point3D(10.0,0,0)));

    }
}
