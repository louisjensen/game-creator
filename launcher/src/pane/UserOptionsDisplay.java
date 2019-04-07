package pane;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class UserOptionsDisplay extends HBox {
    private static final String CREATE_LAUNCHER = "create";
    private static final String PLAY_LAUNCHER = "play";
    public UserOptionsDisplay(){
        this.getStyleClass().add("default.css");
        this.setTranslateY(100);
        this.getChildren().add(0,new LauncherControlDisplay(CREATE_LAUNCHER));
        this.getChildren().add(1, new LauncherControlDisplay(PLAY_LAUNCHER));
        this.setAlignment(Pos.CENTER);
        this.setSpacing(100);

    }
}
