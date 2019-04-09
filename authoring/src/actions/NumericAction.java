package actions;

import engine.external.component.Component;

/**
 * A NumericAction can alter the Double value of a component in three different ways:
 * - setting the existing value to a new value
 * - scaling the existing value by a new value
 * - adding a new value to the existing value
 * @param <Double>
 *
 * @author Feroze
 */
public abstract class NumericAction<Double> extends Action<Double> {
    /**
     * This method is used when subclass objects are constructed in order to specify what kind of
     * operation is being done to the existing value
     * @param type either an absolute set, a scaling, or an addition
     * @param newValue value used in the operation
     * @param componentClass component which this action affects, whose getValue() method should return a DOUBLE
     */
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

    /**
     * This method behaves like the superclass method and just specifies an absolute set operation
     * @param newValue new value for the component
     * @param componentClass class that specifies the component type
     */
    protected void setAbsoluteAction(Double newValue, Class<? extends Component<Double>> componentClass) {
        super.setAbsoluteAction(newValue, componentClass);
    }

    /**
     * This method scales the current value of a component by a scaleFactor
     * @param scaleFactor
     * @param componentClass
     */
    @SuppressWarnings("unchecked")
    protected void setScaledAction(Double scaleFactor, Class<? extends Component<Double>> componentClass) {
        super.setAction((entity) -> {
            Component component = entity.getComponent(componentClass);
            //TODO: FIX
            //component.setValue(((Double)(entity.getComponent(componentClass).getValue())).doubleValue() *
            // scaleFactor);
        });
    }

    /**
     * This method adds a displacementFactor to the current value of a component
     * @param displacementFactor
     * @param componentClass
     */
    protected void setRelativeAction(Double displacementFactor, Class<? extends Component<Double>> componentClass) {
        //TODO: implement
    }

    /**
     * This enum is used in subclass construction to specify what kind of operation should be done to the component
     * value
     */
    enum ModifyType {
        ABSOLUTE,
        RELATIVE,
        SCALE,
    }
}
