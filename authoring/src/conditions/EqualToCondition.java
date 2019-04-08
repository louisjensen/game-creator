package conditions;

import engine.external.component.Component;

public class EqualToCondition extends Condition<Double> {
    public EqualToCondition(Class<? extends Component> component, Double value) {
        setPredicate(entity -> (Double) entity.getComponent(component).getValue() == value);
    }
}
