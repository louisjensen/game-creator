package actions;

import engine.external.component.Component;

public abstract class BooleanAction extends Action<Boolean>  {
    public void setAction(Boolean newValue, Class<? extends Component<Boolean>> componentClass) {
        super.setAbsoluteAction(newValue, componentClass);
    }
}
