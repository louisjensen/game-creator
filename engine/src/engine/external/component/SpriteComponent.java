package engine.external.component;

public class SpriteComponent extends Component<String> {

    private final static String DEFAULT = "no_image.png";

    public SpriteComponent() {
        super(DEFAULT);
    }

    public SpriteComponent(String pathname) {
        super(pathname);
    }
}
