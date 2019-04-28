package engine.external.actions;

import engine.external.component.ProgressionComponent;

public class ProgressionAction extends AddComponentAction {
    public ProgressionAction(Boolean progress){
        setAbsoluteAction(new ProgressionComponent(progress));
    }
}