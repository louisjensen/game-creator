package engine.external.component;

/**
 * @author Lucas Liu
 * @param <T>
 *     Useful component for storing a value which is custom to a particular game. One could store a double, String, or even another Entity
 */
public class ValueComponent extends Component<Double> {

    public ValueComponent(Double t) {
        super(t);
    }
}
