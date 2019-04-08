package actions;

import engine.external.component.Component;

public abstract class StringAction extends Action<String> {
    public void setAction(String newValue, Class<? extends Component<String>> componentClass) {
        super.setAbsoluteAction(newValue, componentClass);
    }
}
