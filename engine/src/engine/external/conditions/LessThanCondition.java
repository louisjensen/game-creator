package engine.external.conditions;

import engine.external.Entity;
import engine.external.component.Component;

import java.io.Serializable;
import java.util.function.Predicate;

public class LessThanCondition extends Condition {
    private String myComponentName;
    private Double myValue;
    private static final String DISPLAY = " Less Than ";
    private static final String COMPONENT = "Component";
    public LessThanCondition(Class<? extends Component> component, Double value) {
        setPredicate((Predicate<Entity> & Serializable) entity -> (Double) entity.getComponent(component).getValue() < value);
        myComponentName = component.getSimpleName();
        myValue = value;
    }
    @Override
    public String toString(){
        return myComponentName.replaceAll(COMPONENT,"") + DISPLAY + myValue;
    }
}
