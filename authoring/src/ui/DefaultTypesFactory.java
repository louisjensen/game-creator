package ui;

import engine.external.Entity;
import engine.external.component.*;
import javafx.geometry.Point3D;

import java.util.Map;
import java.util.ResourceBundle;

public class DefaultTypesFactory {
    private Map<String, Map<String, Entity>> myMap;
    private ResourceBundle myResources;

    private static final String RESOURCES = "dedault_types_factory";

    public DefaultTypesFactory(){
        myResources = ResourceBundle.getBundle(RESOURCES);
    }

    private Entity createCloud(){
        Entity cloud = new Entity();
        GravityComponent gravityComponent = new GravityComponent(0.0);
        NameComponent nameComponent = new NameComponent("Cloud");
        VelocityComponent velocityComponent = new VelocityComponent(new Point3D(10, 0, 0));
        VisibilityComponent visibilityComponent = new VisibilityComponent(true);
        cloud.addComponent(gravityComponent);
        cloud.addComponent(nameComponent);
        cloud.addComponent(velocityComponent);
        cloud.addComponent(visibilityComponent);
        return cloud;
    }

    private Entity createBlock(){
        Entity block = new Entity();
        NameComponent nameComponent = new NameComponent("Block");
        VisibilityComponent visibilityComponent = new VisibilityComponent(true);
        SizeComponent sizeComponent = new SizeComponent(50.0);
        block.addComponent(nameComponent);
        block.addComponent(visibilityComponent);
        block.addComponent(sizeComponent);
        return block;
    }

    //TODO: Add key input and events - collisions
    private Entity createHero(){
        Entity hero = new Entity();
        NameComponent nameComponent = new NameComponent("Hero");
        GravityComponent gravityComponent = new GravityComponent(9.8);
        hero.addComponent(nameComponent);
        hero.addComponent(gravityComponent);
        return hero;
    }
}
