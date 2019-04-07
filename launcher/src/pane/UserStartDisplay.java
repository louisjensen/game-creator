package pane;

import controls.NewPlayerImage;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class UserStartDisplay extends HBox {
    public UserStartDisplay(){
        this.getStyleClass().add("default.css");
        this.setTranslateY(150);
        this.getChildren().add(0,new NewPlayerImage());
        this.getChildren().add(1, new UserLoginDisplay());
        this.setAlignment(Pos.CENTER);
        this.setSpacing(100);

    }
}
