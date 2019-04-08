package actions;

import engine.external.component.SizeComponent;

public class SizeAction extends NumericAction {
    public SizeAction(ModifyType type, Double gravity){
        setAction(type, gravity, SizeComponent.class);
    }
}
