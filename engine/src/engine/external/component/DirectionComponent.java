package engine.external.component;

public class DirectionComponent extends Component<Double> {
    private final static double DEFAULT = 0.0;
    private Double oldValue;
    public static final double PI = 3.14;
    public static final double DIRECTION_ZERO = 0.0;

    public DirectionComponent(){
        super(DEFAULT);
        oldValue = DEFAULT;
    }

    @Override
    public void setValue(Double value) {
        oldValue = Double.valueOf(myValue);
        myValue = value;
    }

    public Double getOldValue() {
        return oldValue;
    }

    public void revertValue(Double value) {
        myValue = Double.valueOf(value);
        oldValue = Double.valueOf(value);
    }
}
