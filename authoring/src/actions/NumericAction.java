package actions;

import engine.external.Entity;
import engine.external.component.Component;

import java.util.function.Consumer;

public abstract class NumericAction<Double> extends Action<Double> {
    public void setAction(ModifyType type, Double newValue, Class<? extends Component<Double>> componentClass) {
        switch (type) {
            case ABSOLUTE:
                setAbsoluteAction(newValue, componentClass);
                break;
            case SCALE:
                setScaledAction(newValue, componentClass);
                break;
            case RELATIVE:
                setRelativeAction(newValue, componentClass);
                break;
        }
    }

    public void setAbsoluteAction(Double newValue, Class<? extends Component<Double>> componentClass) {
        super.setAbsoluteAction(newValue, componentClass);
    }

    @SuppressWarnings("unchecked")
    protected void setScaledAction(Double scaleFactor, Class<? extends Component<Double>> componentClass) {
        super.setAction((entity) -> {
            Component component = entity.getComponent(componentClass);
            //TODO: FIX
            //component.setValue(((Double)(entity.getComponent(componentClass).getValue())).doubleValue() *
            // scaleFactor);
        });
    }

    protected void setRelativeAction(Double displacementFactor, Class<? extends Component<Double>> componentClass) {
        //TODO: implement
    }

    enum ModifyType {
        ABSOLUTE,
        RELATIVE,
        SCALE,
    }
}
