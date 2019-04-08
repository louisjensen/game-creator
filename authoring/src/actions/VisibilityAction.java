package actions;

import engine.external.Entity;
import engine.external.component.VisibilityComponent;

import java.util.function.Consumer;

public class VisibilityAction extends BooleanAction {
    public VisibilityAction(Boolean isVisible){
        setAction(isVisible, VisibilityComponent.class);
    }
}
