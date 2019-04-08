package actions;

import engine.external.Entity;
import engine.external.component.GravityComponent;

import java.util.function.Consumer;

public class GravityAction extends NumericAction {
    public GravityAction(ModifyType type, Double gravity){
        setAction(type, gravity, GravityComponent.class);
    }
}
