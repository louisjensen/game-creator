package engine.external.component;

public class DirectionComponent extends Component<Double> {
    private final static double DEFAULT = 0.0;
    public static final double PI = 3.14;
    public static final double DIRECTION_ZERO = 0.0;

    public DirectionComponent(){
        super(DEFAULT);
    }

    @Override
    public void setValue(Double value) {
        myValue = value;
    }
}
